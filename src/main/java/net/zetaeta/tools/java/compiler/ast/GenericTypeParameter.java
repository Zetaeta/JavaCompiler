package net.zetaeta.tools.java.compiler.ast;

import java.util.List;

/**
 * A generic parameter declaration, e.g. for a class or a method.
 * @author daniel
 *
 */
public class GenericTypeParameter extends GenericParameter {
    protected String typeName;
    protected List<TypeName> extending;
    
    public GenericTypeParameter(String typeName) {
        this.typeName = typeName;
    }
    
    public GenericTypeParameter(String typeName, List<TypeName> extending) {
        this(typeName);
        this.extending = extending;
    }
    
}
