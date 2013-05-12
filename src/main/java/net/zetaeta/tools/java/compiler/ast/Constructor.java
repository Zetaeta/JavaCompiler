package net.zetaeta.tools.java.compiler.ast;

import java.util.List;

public class Constructor extends SolidMethod {

    public Constructor(TypeName returns, Modifiers mods,
            List<Annotation> annot, List<GenericTypeParameter> genParams,
            List<ParameterDeclaration> params, List<TypeName> exceptions, Block body) {
        super(returns.getName(), returns, mods, annot, genParams, params, exceptions, body);
    }

}
