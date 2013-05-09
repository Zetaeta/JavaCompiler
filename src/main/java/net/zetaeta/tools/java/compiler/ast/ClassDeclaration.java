package net.zetaeta.tools.java.compiler.ast;

import java.util.ArrayList;
import java.util.List;

public class ClassDeclaration extends ClassOrInterfaceDeclaration {
    
    private TypeName superClass;
    private List<TypeName> interfaces;
    private List<Member> members;
    
    @Override
    protected List<TreeNode> getChildList() {
        return new ArrayList<>();
    }
}
