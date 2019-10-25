package ir.iahrari.githubseeker.service.util

import android.webkit.MimeTypeMap
import ir.iahrari.githubseeker.service.model.ContentType
import com.pddstudio.highlightjs.models.Language
import kotlin.math.round

fun prepareFileSize(size: Int): String{
    return when {
        size >= 1024 * 1024 * 1024 -> (round(size.toFloat()/(1024 * 1024 * 1024) *100) / 100).toString() + " GB"
        size >= 1024 * 1024 -> (round(size.toFloat()/(1024*1024) *100) / 100).toString() + " MB"
        size >= 1024 -> (round(size.toFloat()/1024 * 100) / 100).toString() + " KB"
        else -> "$size B"
    }
}

fun getContentDataType(name: String): ContentType {
    val fileType = getFileType(name)
    return when {
        fileType == "*/*" || fileType.contains("text") -> {
            if (name.endsWith(".md,.markdowm,.wiki", 1))
                ContentType.Markdown
            else
                ContentType.Code
        }
        fileType.contains("image") -> ContentType.Image
        else -> ContentType.NotSupported
    }

}

fun getFileType(name: String): String{
    var extension = ""
    val i = name.lastIndexOf('.')
    if (i > 0) extension = name.substring(i + 1)
    return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension) ?: "*/*"
}

fun findLanguageFromName(name: String): Language{
    return when{
        name.endsWith(".abnf") -> Language.ABNF
        name.endsWith(".accesslog") -> Language.ACCESS_LOGS
        name.endsWith(".ada") -> Language.ADA
        name.endsWith(".armasm,.arm", 1) -> Language.ARM_ASSEMBLER
        name.endsWith(".avrasm") -> Language.AVR_ASSEMBLER
        name.endsWith(".apache,.apacheconf", 1) -> Language.APACHE
        name.endsWith(".applescript,.osascript", 1) -> Language.APPLE_SCRIPT
        name.endsWith(".asciidoc,.adoc", 1) -> Language.ASCII_DOC
        name.endsWith(".aspectj") -> Language.ASPECT_J
        name.endsWith(".autohotkey") -> Language.AUTO_HOTKEY
        name.endsWith(".autoit") -> Language.AUTO_IT
        name.endsWith(".awk,.mawk,.nawk,.gawk", 1) -> Language.AWK
        name.endsWith(".axapta") -> Language.AXAPTA
        name.endsWith(".bash,.sh,.zsh", 1) -> Language.BASH
        name.endsWith(".basic") -> Language.BASIC
        name.endsWith(".bnf") -> Language.BNF
        name.endsWith(".brainfuck,.bf", 1) -> Language.BRAINFUCK
        name.endsWith(".cs,.csharp", 1) -> Language.C_SHARP
        name.endsWith(".cpp,.c,.cc,.h,.c++,.h++,.hpp", 1) -> Language.C_PLUS_PLUS
        name.endsWith(".cos,.cls", 1) -> Language.CACHE_OBJECT_SCRIPT
        name.endsWith(".cmake,.cmake.in", 1) -> Language.C_MAKE
        name.endsWith(".coq") -> Language.COQ
        name.endsWith(".csp") -> Language.CSP
        name.endsWith(".css") -> Language.CSS
        name.endsWith(".capnproto,.capnp", 1) -> Language.CAPTAIN_PROTO
        name.endsWith(".clojure,.clj", 1) -> Language.CLOJURE
        name.endsWith(".coffeescript,.coffee,.cson,.iced", 1) -> Language.COFFEE_SCRIPT
        name.endsWith(".crmsh,.crm,.pcmk", 1) -> Language.CRMSH
        name.endsWith(".crystal,.cr", 1) -> Language.CRYSTAL
        name.endsWith(".d") -> Language.D
        name.endsWith(".dns,.zone,.bind", 1) -> Language.DNS_ZONE_FILE
        name.endsWith(".dos,.bat,.cmd", 1) -> Language.DOS
        name.endsWith(".dart") -> Language.DART
        name.endsWith(".delphi,.dpr,.dfm,.pas,.pascal,.freepascal,.lazarus,.lpr,.lfm", 1) -> Language.DELPHI
        name.endsWith(".diff,.patch", 1) -> Language.DIFF
        name.endsWith(".django,.jinja", 1) -> Language.DJANGO
        name.endsWith(".dockerfile,.docker", 1) -> Language.DOCKER_FILE
        name.endsWith(".dsconfig") -> Language.DSCONFIG
        name.endsWith(".dts") -> Language.DTS
        name.endsWith(".dust,.dst", 1) -> Language.DUST
        name.endsWith(".ebnf") -> Language.EBNF
        name.endsWith(".elixir") -> Language.ELIXIR
        name.endsWith(".elm") -> Language.ELM
        name.endsWith(".erlang,.erl", 1) -> Language.ERLANG
        name.endsWith(".excel,.xls,.xlsx", 1) -> Language.EXCEL
        name.endsWith(".fsharp,.fs", 1) -> Language.F_SHARP
        name.endsWith(".fix") -> Language.FIX
        name.endsWith(".fortran,.f90,.f95", 1) -> Language.FORTRAN
        name.endsWith(".gcode,.nc", 1) -> Language.G_CODE
        name.endsWith(".gams,.gms", 1) -> Language.GAMS
        name.endsWith(".gauss,.gss", 1) -> Language.GAUSS
        name.endsWith(".gherkin") -> Language.GHERKIN
        name.endsWith(".go,.golang", 1) -> Language.GO
        name.endsWith(".golo,.gololang", 1) -> Language.GOLO
        name.endsWith(".gradle") -> Language.GRADLE
        name.endsWith(".groovy") -> Language.GROOVY
        name.endsWith(".html,.xhtml", 1) -> Language.HTML
        name.endsWith(".xml,.rss,.atom,.xjb,.xsd,.xsl,.list", 1) -> Language.XML
        name.endsWith(".http,.https", 1) -> Language.HTTP
        name.endsWith(".haml") -> Language.HAML
        name.endsWith(".handlebars,.hbs,.html.hbs,.html.handlebars", 1) -> Language.HANDLEBARS
        name.endsWith(".haskell,.hs", 1) -> Language.HASKELL
        name.endsWith(".haxe,.hx", 1) -> Language.HAXE
        name.endsWith(".hy,.hylang", 1) -> Language.HY
        name.endsWith(".ini") -> Language.INI
        name.endsWith(".inform7,.i7", 1) -> Language.INFORM7
        name.endsWith(".irpf90") -> Language.IRPF90
        name.endsWith(".json") -> Language.JSON
        name.endsWith(".java, .jsp", 1) -> Language.JAVA
        name.endsWith(".javascript,.js,.jsx", 1) -> Language.JAVA_SCRIPT
        name.endsWith(".leaf") -> Language.LEAF
        name.endsWith(".lasso,.ls,.lassoscript", 1) -> Language.LASSO
        name.endsWith(".less") -> Language.LESS
        name.endsWith(".ldif") -> Language.LDIF
        name.endsWith(".lisp") -> Language.LISP
        name.endsWith(".livecodeserver") -> Language.LIVE_CODE_SERVER
        name.endsWith(".livescript,ls", 1) -> Language.LIVE_SCRIPT
        name.endsWith(".lua") -> Language.LUA
        name.endsWith(".makefile,.mk,.mak", 1) -> Language.MAKEFILE
        name.endsWith(".markdown,.md,.mkdown,.mkd", 1) -> Language.MARKDOWN
        name.endsWith(".mathematica,.mma", 1) ->Language.MATHEMATICA
        name.endsWith(".matlab") -> Language.MATLAB
        name.endsWith(".maxima") -> Language.MAXIMA
        name.endsWith(".mel") -> Language.MAYA_EMBEDDED_LANGUAGE
        name.endsWith(".mercury") -> Language.MERCURY
        name.endsWith(".mizar") -> Language.MIZAR
        name.endsWith(".mojolicious") -> Language.MOJOLICIOUS
        name.endsWith(".monkey") -> Language.MONKEY
        name.endsWith(".moonscript,.moon", 1) -> Language.MOONSCRIPT
        name.endsWith(".n1ql") -> Language.N1QL
        name.endsWith(".nsis") -> Language.NSIS
        name.endsWith(".nginx,.nginxconf", 1) -> Language.NGINX
        name.endsWith(".nimrod,.nim", 1) -> Language.NIMROD
        name.endsWith(".nix") -> Language.NIX
        name.endsWith(".ocaml,.ml", 1) -> Language.O_CAML
        name.endsWith(".objectivec,.mm,.objc,.obj-c", 1) -> Language.OBJECTIVE_C
        name.endsWith(".glsl") -> Language.OPENGL_SHADING_LANGUAGE
        name.endsWith(".openscad,.scad", 1) -> Language.OPEN_SCAD
        name.endsWith(".ruleslanguage") -> Language.ORACLE_RULES_LANGUAGE
        name.endsWith(".oxygene") -> Language.OXYGENE
        name.endsWith(".pf,.pf.conf", 1) -> Language.PF
        name.endsWith(".php,.php3,.php4,.php5,.php6", 1) ->Language.PHP
        name.endsWith(".parser3") -> Language.PARSER3
        name.endsWith(".perl,.pl,.pm", 1) -> Language.PERL
        name.endsWith(".pony") -> Language.PONY
        name.endsWith(".powershell,.ps", 1) -> Language.POWER_SHELL
        name.endsWith(".processing") -> Language.PROCESSING
        name.endsWith(".prolog") -> Language.PROLOG
        name.endsWith(".protobuf") -> Language.PROTOCOL_BUFFERS
        name.endsWith(".puppet,.pp", 1) -> Language.PUPPET
        name.endsWith(".python,.py,.gyp", 1) -> Language.PYTHON
        name.endsWith(".profile") -> Language.PYTHON
        name.endsWith(".k,.kdb", 1) -> Language.Q
        name.endsWith(".qml") -> Language.QML
        name.endsWith(".r") -> Language.R
        name.endsWith(".rib") -> Language.RENDER_MAN_RIB
        name.endsWith(".rsl") -> Language.RENDER_MAN_RSL
        name.endsWith(".graph,.instances", 1) -> Language.ROBOCONF
        name.endsWith(".ruby,.rb,.gemspec,.podspec,.thor,.irb", 1) -> Language.RUBY
        name.endsWith(".rust,.rs", 1) -> Language.RUST
        name.endsWith(".scss") -> Language.SCSS
        name.endsWith(".sql") -> Language.SQL
        name.endsWith(".p21,.step,.stp", 1) -> Language.STEP_PART_21
        name.endsWith(".scala") -> Language.SCALA
        name.endsWith(".scheme") -> Language.SCHEME
        name.endsWith(".scilab,.sci", 1) -> Language.SCILAB
        name.endsWith(".shell,.console", 1) -> Language.SHELL
        name.endsWith(".smali") -> Language.SMALI
        name.endsWith(".smalltalk,.st", 1) -> Language.SMALLTALK
        name.endsWith(".stan") -> Language.STAN
        name.endsWith(".stata") -> Language.STATA
        name.endsWith(".stylus,.styl", 1) -> Language.STYLUS
        name.endsWith(".subunit") -> Language.SUB_UNIT
        name.endsWith(".swift") -> Language.SWIFT
        name.endsWith(".tap") -> Language.TEST_ANYTHING_PROTOCOL
        name.endsWith(".tcl,.tk", 1) -> Language.TCL
        name.endsWith(".tex") -> Language.TEX
        name.endsWith(".thrift") -> Language.THRIFT
        name.endsWith(".tp") -> Language.TP
        name.endsWith(".twig,.craftcms", 1) -> Language.TWIG
        name.endsWith(".typescript,.ts", 1) -> Language.TYPE_SCRIPT
        name.endsWith(".vbnet,.vb", 1) -> Language.VB_NET
        name.endsWith(".vbscript,.vbs", 1) -> Language.VB_SCRIPT
        name.endsWith(".vhdl") -> Language.VHDL
        name.endsWith(".vala") -> Language.VALA
        name.endsWith(".verilog,.v", 1) -> Language.VERILOG
        name.endsWith(".vim") -> Language.VIM
        name.endsWith(".x86asm") -> Language.X86_ASSEMBLY
        name.endsWith(".xl,.tao", 1) -> Language.XL
        name.endsWith(".xpath,.xq", 1) -> Language.X_QUERY
        name.endsWith(".zephir,.zep", 1) -> Language.ZEPHIR
        else -> Language.AUTO_DETECT
    }
}