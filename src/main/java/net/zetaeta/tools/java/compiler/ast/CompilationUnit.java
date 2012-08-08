package net.zetaeta.tools.java.compiler.ast;

import java.util.ArrayList;
import java.util.List;

public class CompilationUnit extends TreeNode {
    private String packageName;
    private List<String> imports;
    private List<ClassOrInterfaceDeclaration> declarations = new ArrayList<>();
    
    public void setPackage(String pkg) {
        this.packageName = pkg;
    }
    
    public void addImport(String imp) {
        imports.add(imp);
    }
    
    public void addDeclaration(ClassOrInterfaceDeclaration decl) {
        declarations.add(decl);
    }
}
