package net.zetaeta.tools.java.compiler.ast;

public class GenericObjectParameter extends GenericParameter {
    private TypeName name;
    
    public GenericObjectParameter(TypeName name) {
        this.name = name;
    }
    
    public GenericObjectParameter() {
        
    }
}
