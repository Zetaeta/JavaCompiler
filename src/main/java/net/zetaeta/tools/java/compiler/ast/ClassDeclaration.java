package net.zetaeta.tools.java.compiler.ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassDeclaration extends ClassOrInterfaceDeclaration {
    private String name;
    private List<GenericTypeParameter> genericParams;
    private TypeName superClass;
    private List<TypeName> interfaces;
    private List<Member> members;
    
    public ClassDeclaration(String name, Modifiers mods, List<Annotation> annot, List<GenericTypeParameter> genericParams, TypeName superClass, List<TypeName> interfaces, List<Member> members) {
        super(mods, annot);
        this.name = name;
        this.genericParams = genericParams;
        this.superClass = superClass;
        this.interfaces = interfaces;
        this.members = members;
    }
    
    @Override
    protected Map<?, ?> getAttributes() {
        Map<Object, Object> attrs = new HashMap<>();
        attrs.putAll(super.getAttributes());
        attrs.put("Name", name);
        attrs.put("Superclass", superClass.toString());
        return attrs;
    }
    
    @Override
    protected List<TreeNode> getChildList() {
        List<TreeNode> list = new ArrayList<>();
        list.addAll(genericParams);
        list.addAll(interfaces);
        list.addAll(members);
        return list;
    }
}
