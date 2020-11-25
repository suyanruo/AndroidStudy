package com.example.modulepluginlib.transform

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.example.modulepluginlib.addlog.TestMethodClassVisitor
import org.apache.commons.io.FileUtils
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter

class AsmTransform extends Transform {
    Project project

    AsmTransform(Project project) {
        this.project = project
    }

    @Override
    String getName() {
        return AsmTransform.simpleName
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        /**
         * 输入类型，可以使class文件，也可以是源码文件。
         * CLASSES类型表示的是在jar包或者文件夹中的.class文件；RESOURCES类型表示的是标准的Java源文件。
         */
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        // 作用范围
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        // 是否支持增量编译，返回true的话表示可以根据 com.android.build.api.transform.TransformInput 来获得更改、移除或者添加的文件目录或者jar包。
        return true
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        // 在这里对输入输出的class进行处理
        println("===== ASM Transform =====")
//        println("${transformInvocation.inputs}")
//        println("${transformInvocation.referencedInputs}")
//        println("${transformInvocation.outputProvider}")
//        println("${transformInvocation.incremental}")

        //当前是否是增量编译
        boolean isIncremental = transformInvocation.isIncremental()
        //消费型输入，可以从中获取jar包和class文件夹路径。需要输出给下一个任务
        Collection<TransformInput> inputs = transformInvocation.getInputs()
        //引用型输入，无需输出。
        Collection<TransformInput> referencedInputs = transformInvocation.getReferencedInputs()
        //OutputProvider管理输出路径，如果消费型输入为空，你会发现OutputProvider == null
        TransformOutputProvider outputProvider = transformInvocation.getOutputProvider()
        for (TransformInput input : inputs) {
            for (JarInput jarInput : input.getJarInputs()) {
                File dest = outputProvider.getContentLocation(
                        jarInput.getFile().getAbsolutePath(),
                        jarInput.getContentTypes(),
                        jarInput.getScopes(),
                        Format.JAR)
                //将修改过的字节码copy到dest，就可以实现编译期间干预字节码的目的了
                transformJar(jarInput.getFile(), dest)
            }
            for (DirectoryInput directoryInput : input.getDirectoryInputs()) {
                println("== DI = " + directoryInput.file.listFiles().toArrayString())
                File dest = outputProvider.getContentLocation(directoryInput.getName(),
                        directoryInput.getContentTypes(), directoryInput.getScopes(),
                        Format.DIRECTORY)
                //将修改过的字节码copy到dest，就可以实现编译期间干预字节码的目的了
                //FileUtils.copyDirectory(directoryInput.getFile(), dest)
                transformDir(directoryInput.getFile(), dest)
            }
        }
    }

    private static void transformJar(File input, File dest) {
        println("=== transformJar ===")
        FileUtils.copyFile(input, dest)
    }

    private static void transformDir(File input, File dest) {
        if (dest.exists()) {
            FileUtils.forceDelete(dest)
        }
        FileUtils.forceMkdir(dest)
        String srcDirPath = input.getAbsolutePath()
        String destDirPath = dest.getAbsolutePath()
        println("=== transform dir = " + srcDirPath + ", " + destDirPath)
        for (File file : input.listFiles()) {
            String destFilePath = file.absolutePath.replace(srcDirPath, destDirPath)
            File destFile = new File(destFilePath)
            if (file.isDirectory()) {
                transformDir(file, destFile)
            } else if (file.isFile()) {
                FileUtils.touch(destFile)
                transformSingleFile(file, destFile)
            }
        }
    }

    private static void transformSingleFile(File input, File dest) {
        println("=== transformSingleFile ===")
        if (input.name.contains("TestAsm")) {//只对TestAsm类做加工处理
            weave(input.getAbsolutePath(), dest.getAbsolutePath())
        } else {
            FileUtils.copyFile(input, dest)
        }
    }

    private static void weave(String inputPath, String outputPath) {
        try {
            FileInputStream is = new FileInputStream(inputPath)
            ClassReader cr = new ClassReader(is)
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES)
            TestMethodClassVisitor adapter = new TestMethodClassVisitor(cw)
            cr.accept(adapter, 0)
            FileOutputStream fos = new FileOutputStream(outputPath)
            fos.write(cw.toByteArray())
            fos.close()
        } catch (IOException e) {
            e.printStackTrace()
        }
    }

}