package net.zetaeta.tools.java.compiler.ast;

import java.util.List;

public class WildcardGenericParameter extends GenericObjectParameter {

    protected List<TypeName> extending;
    protected List<TypeName> supering;
    
    public WildcardGenericParameter() {
        
    }
    
    public WildcardGenericParameter(List<TypeName> extending) {
        this.extending = extending;
    }
    
    public WildcardGenericParameter(List<TypeName> extending, List<TypeName> supering) {
        this(extending);
        this.supering = supering;
    }
}
