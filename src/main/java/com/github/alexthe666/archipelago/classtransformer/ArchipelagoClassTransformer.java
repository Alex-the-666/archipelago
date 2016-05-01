package com.github.alexthe666.archipelago.classtransformer;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArchipelagoClassTransformer implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] classBytes) {
        boolean obf;
        ClassNode classNode = new ClassNode();
        if ((obf = "bfk".equals(name)) || "net.minecraft.client.renderer.EntityRenderer".equals(name)) {
            ClassReader classReader = new ClassReader(classBytes);
            classReader.accept(classNode, 0);
            String setupFogName = obf ? "a" : "setupFog";
            String setupFogDesc = "(IF)V";
            for (MethodNode methodNode : classNode.methods) {
                if (setupFogName.equals(methodNode.name) && setupFogDesc.equals(methodNode.desc)) {
                    InsnList inject = new InsnList();
                    List<AbstractInsnNode> nodesInLine = new ArrayList<AbstractInsnNode>();
                    for (AbstractInsnNode node : methodNode.instructions.toArray()) {
                        boolean target = false;
                        if (node instanceof LabelNode) {
                            nodesInLine.add(node);
                            for (AbstractInsnNode lineNode : nodesInLine) {
                                if (lineNode instanceof MethodInsnNode) {
                                    MethodInsnNode method_0 = (MethodInsnNode) lineNode;
                                    if ((method_0.name.equals("getRespirationModifier") && method_0.owner.equals("net/minecraft/enchantment/EnchantmentHelper")) || (method_0.name.equals("a") && method_0.owner.equals("ack"))) {
                                        target = true;
                                    }
                                }
                            }
                            if (target) {
                                for (AbstractInsnNode lineNode : nodesInLine) {
                                    if (!(lineNode instanceof LabelNode)) {
                                        inject.remove(lineNode);
                                    }
                                    if (lineNode instanceof JumpInsnNode) {
                                        break;
                                    }
                                }
                            }
                            nodesInLine.clear();
                        }
                        if (node.getOpcode() == Opcodes.RETURN) {
                            inject.add(new VarInsnNode(Opcodes.ALOAD, 3));
                            inject.add(new VarInsnNode(Opcodes.ALOAD, 4));
                            inject.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/github/alexthe666/archipelago/classtransformer/ArchipelagoHooks", "renderUnderwaterFog", "(L" + (obf ? "rr" : "net/minecraft/entity/Entity") + ";L" + (obf ? "arc" : "net/minecraft/block/state/IBlockState") + ";)V", false));
                        }
                        if (!target) {
                            inject.add(node);
                            nodesInLine.add(node);
                        }
                    }
                    methodNode.instructions.clear();
                    methodNode.instructions.add(inject);
                    break;
                }
            }
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            classNode.accept(classWriter);
            saveBytecode(name, classWriter);
            return classWriter.toByteArray();
        }
        return classBytes;
    }

    private void saveBytecode(String name, ClassWriter cw) {
        try {
            File debugDir = new File("archipelago/debug/");
            debugDir.mkdirs();
            FileOutputStream out = new FileOutputStream(new File(debugDir, name + ".class"));
            out.write(cw.toByteArray());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
