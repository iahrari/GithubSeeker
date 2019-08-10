package com.example.retrofitproject

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.example.retrofitproject.databinding.FragmentRepoBinding
import kotlinx.android.synthetic.main.fragment_repo.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RepoFragment : Fragment() {
    private lateinit var repo: Repo
    private lateinit var adapter: ContentListAdapter
    private lateinit var binding: FragmentRepoBinding
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.fragment_repo, container, false)
        val args by navArgs<RepoFragmentArgs>()
        repo = args.repo
        activity?.findViewById<TextView>(R.id.header_title)?.text = repo.name

        return binding.root
    }
//[kapt] An exception occurred: android.databinding.tool.util.LoggedErrorException: Found data binding errors.
//****/ data binding error ****msg:Could not find accessor com.example.retrofitproject.Repo.gitURL file:/home/iman/Programming/Projects/Android/AndroidLearn/RetrofitProject/app/src/main/res/layout/fragment_repo.xml loc:112:95 - 112:105 ****\ data binding error ****
//
//	at android.databinding.tool.processing.Scope.assertNoError(Scope.java:112)
//	at android.databinding.annotationprocessor.ProcessDataBinding.doProcess(ProcessDataBinding.java:109)
//	at android.databinding.annotationprocessor.ProcessDataBinding.process(ProcessDataBinding.java:73)
//	at org.jetbrains.kotlin.kapt3.base.incremental.IncrementalProcessor.process(incrementalProcessors.kt)
//	at org.jetbrains.kotlin.kapt3.base.ProcessorWrapper.process(annotationProcessing.kt:132)
//	at com.sun.tools.javac.processing.JavacProcessingEnvironment.callProcessor(JavacProcessingEnvironment.java:794)
//	at com.sun.tools.javac.processing.JavacProcessingEnvironment.access$200(JavacProcessingEnvironment.java:91)
//	at com.sun.tools.javac.processing.JavacProcessingEnvironment$DiscoveredProcessors$ProcessorStateIterator.runContributingProcs(JavacProcessingEnvironment.java:627)
//	at com.sun.tools.javac.processing.JavacProcessingEnvironment$Round.run(JavacProcessingEnvironment.java:1033)
//	at com.sun.tools.javac.processing.JavacProcessingEnvironment.doProcessing(JavacProcessingEnvironment.java:1198)
//	at com.sun.tools.javac.main.JavaCompiler.processAnnotations(JavaCompiler.java:1170)
//	at com.sun.tools.javac.main.JavaCompiler.processAnnotations(JavaCompiler.java:1068)
//	at org.jetbrains.kotlin.kapt3.base.AnnotationProcessingKt.doAnnotationProcessing(annotationProcessing.kt:80)
//	at org.jetbrains.kotlin.kapt3.base.AnnotationProcessingKt.doAnnotationProcessing$default(annotationProcessing.kt:36)
//	at org.jetbrains.kotlin.kapt3.AbstractKapt3Extension.runAnnotationProcessing(Kapt3Extension.kt:223)
//	at org.jetbrains.kotlin.kapt3.AbstractKapt3Extension.analysisCompleted(Kapt3Extension.kt:187)
//	at org.jetbrains.kotlin.kapt3.ClasspathBasedKapt3Extension.analysisCompleted(Kapt3Extension.kt:98)
//	at org.jetbrains.kotlin.cli.jvm.compiler.TopDownAnalyzerFacadeForJVM$analyzeFilesWithJavaIntegration$2.invoke(TopDownAnalyzerFacadeForJVM.kt:95)
//	at org.jetbrains.kotlin.cli.jvm.compiler.TopDownAnalyzerFacadeForJVM.analyzeFilesWithJavaIntegration(TopDownAnalyzerFacadeForJVM.kt:105)
//	at org.jetbrains.kotlin.cli.jvm.compiler.TopDownAnalyzerFacadeForJVM.analyzeFilesWithJavaIntegration$default(TopDownAnalyzerFacadeForJVM.kt:80)
//	at org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler$analyze$1.invoke(KotlinToJVMBytecodeCompiler.kt:395)
//	at org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler$analyze$1.invoke(KotlinToJVMBytecodeCompiler.kt:65)
//	at org.jetbrains.kotlin.cli.common.messages.AnalyzerWithCompilerReport.analyzeAndReport(AnalyzerWithCompilerReport.kt:107)
//	at org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler.analyze(KotlinToJVMBytecodeCompiler.kt:386)
//	at org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler.compileModules$cli(KotlinToJVMBytecodeCompiler.kt:118)
//	at org.jetbrains.kotlin.cli.jvm.K2JVMCompiler.doExecute(K2JVMCompiler.kt:166)
//	at org.jetbrains.kotlin.cli.jvm.K2JVMCompiler.doExecute(K2JVMCompiler.kt:56)
//	at org.jetbrains.kotlin.cli.common.CLICompiler.execImpl(CLICompiler.kt:84)
//	at org.jetbrains.kotlin.cli.common.CLICompiler.execImpl(CLICompiler.kt:42)
//	at org.jetbrains.kotlin.cli.common.CLITool.exec(CLITool.kt:104)
//	at org.jetbrains.kotlin.daemon.CompileServiceImpl$compile$1$1$2.invoke(CompileServiceImpl.kt:442)
//	at org.jetbrains.kotlin.daemon.CompileServiceImpl$compile$1$1$2.invoke(CompileServiceImpl.kt:102)
//	at org.jetbrains.kotlin.daemon.CompileServiceImpl$doCompile$$inlined$ifAlive$lambda$2.invoke(CompileServiceImpl.kt:1005)
//	at org.jetbrains.kotlin.daemon.CompileServiceImpl$doCompile$$inlined$ifAlive$lambda$2.invoke(CompileServiceImpl.kt:102)
//	at org.jetbrains.kotlin.daemon.common.DummyProfiler.withMeasure(PerfUtils.kt:138)
//	at org.jetbrains.kotlin.daemon.CompileServiceImpl.checkedCompile(CompileServiceImpl.kt:1047)
//	at org.jetbrains.kotlin.daemon.CompileServiceImpl.doCompile(CompileServiceImpl.kt:1004)
//	at org.jetbrains.kotlin.daemon.CompileServiceImpl.compile(CompileServiceImpl.kt:441)
//	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
//	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
//	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
//	at java.lang.reflect.Method.invoke(Method.java:498)
//	at sun.rmi.server.UnicastServerRef.dispatch(UnicastServerRef.java:346)
//	at sun.rmi.transport.Transport$1.run(Transport.java:200)
//	at sun.rmi.transport.Transport$1.run(Transport.java:197)
//	at java.security.AccessController.doPrivileged(Native Method)
//	at sun.rmi.transport.Transport.serviceCall(Transport.java:196)
//	at sun.rmi.transport.tcp.TCPTransport.handleMessages(TCPTransport.java:568)
//	at sun.rmi.transport.tcp.TCPTransport$ConnectionHandler.run0(TCPTransport.java:826)
//	at sun.rmi.transport.tcp.TCPTransport$ConnectionHandler.lambda$run$0(TCPTransport.java:683)
//	at java.security.AccessController.doPrivileged(Native Method)
//	at sun.rmi.transport.tcp.TCPTransport$ConnectionHandler.run(TCPTransport.java:682)
//	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
//	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
//	at java.lang.Thread.run(Thread.java:745)
//
//
//> Task :app:buildInfoGeneratorDebug
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ContentListAdapter(true)
        scope.launch {
            content_recycler.addItemDecoration(MiddleDividerItemDecoration(context!!, MiddleDividerItemDecoration.VERTICAL, 59))
            content_recycler.adapter = adapter
            getContents()

            binding.repo = repo
//            repo_name.text = repo.name
//            repo_language.text = repo.language
//            repo_description.text = repo.description
//            if (repo.private)
//                repo_name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_locked, 0, 0, 0)
//            repo_owner.text = "Owner: ${repo.owner.username}"
//            repo_html_url.text = "URL: ${repo.hURL}"
//            repo_clone_url.text = "Clone URL: ${repo.cloneURL}"
//            repo_git_url.text = "Git URL: ${repo.gitURl}"
//            repo_ssh_url.text = "SSH URL: ${repo.sshURL}"
//            repo_size.text = "Contains: ${repo.size} files"
            if (repo.language != null)
                repo_lang_logo.setImageResource(
                    when (repo.language) {
                        "Kotlin" -> R.drawable.ic_kotlin
                        "Java" -> R.drawable.ic_java
                        "Go" -> R.drawable.ic_go
                        else -> R.drawable.ic_github
                    }
                )
            else
                repo_lang_logo.setImageResource(android.R.drawable.stat_notify_error)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun getContents(){
        scope.launch {
            try {
                val name = repo.path!!.split("/")
                val response = client.getSingleRepo(context!!.getToken()!!, name[0], name[1])
                if (response.isSuccessful)
                    adapter.submitList(response.body() as MutableList)

                else
                    context!!.processResponseCode(response.code())
            } catch (t: Throwable){
                if (context != null)
                    Toast.makeText(context, t.message + "m", Toast.LENGTH_LONG).show()
            }
        }
    }
}
