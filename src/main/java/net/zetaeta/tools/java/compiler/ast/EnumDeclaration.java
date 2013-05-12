package net.zetaeta.tools.java.compiler.ast;

import java.util.List;

public class EnumDeclaration extends ClassDeclaration {

    public EnumDeclaration(String name, Modifiers mods, List<Annotation> annot,
            List<GenericTypeParameter> genericParams, TypeName superClass,
            List<TypeName> interfaces, List<Member> members) {
        super(name, mods, annot, genericParams, superClass, interfaces, members);
    }

}
