package net.zetaeta.tools.java.compiler.ast;

import java.util.List;

public class InterfaceDeclaration extends ClassOrInterfaceDeclaration {

    protected InterfaceDeclaration(Modifiers mods, List<Annotation> annot) {
        super(mods, annot);
    }

}
