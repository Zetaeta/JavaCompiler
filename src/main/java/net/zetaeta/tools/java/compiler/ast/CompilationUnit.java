package net.zetaeta.tools.java.compiler.ast;

import java.util.ArrayList;
import java.util.List;

public class CompilationUnit extends TreeNode {
    private String packageName;
    private List<String> imports = new ArrayList<>();
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
    
    @Override
    protected List<TreeNode> getChildList() {
        return new ArrayList<TreeNode>(declarations);
    }
    
    @Override
    public String extraToString() {
        StringBuilder sb = new StringBuilder();
//        sb.append(getClass().getSimpleName()).append(" {\n");
        if (packageName != null && !packageName.isEmpty()) {
            sb.append(TAB_SIZE).append("Package: ").append(packageName).append('\n');
        }
        if (imports.size() > 0) {
            sb.append(TAB_SIZE).append("Imports {\n");
            for (String s : imports) {
                sb.append(TAB_SIZE).append(TAB_SIZE).append(s).append('\n');
            }
            sb.append(TAB_SIZE).append("}\n");
        }
/*        for (TreeNode tn : declarations) {
            for (String s : tn.toString().split(" ")) {
                sb.append(TAB_SIZE).append(s).append('\n');
            }
        }
        sb.append("}\n"); */
        return sb.toString();
    }
    
}
