package net.zetaeta.tools.java.compiler.ast;

import java.util.List;

public class SolidMethod extends Method {
    protected Block body;
    
    public SolidMethod(String name, TypeName returns, Modifiers mods, List<Annotation> annot, List<GenericTypeParameter> genParams, List<ParameterDeclaration> params, List<TypeName> exceptions, Block body) {
        super(name, returns, mods, annot, genParams, params, exceptions);
        this.body = body;
    }
}
