package net.zetaeta.tools.java.compiler.ast;

import java.util.List;

public class PackageDeclaration extends AnnotatedTreeNode {
    protected PackageDeclaration(Modifiers mods, List<Annotation> annot) {
        super(mods, annot);
        // TODO Auto-generated constructor stub
    }

    private String packageName;
}
