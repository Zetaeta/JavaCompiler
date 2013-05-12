package net.zetaeta.tools.java.compiler.ast;

/**
 * A generic parameter instantiation in a variable declaration. For example,
 * {@code
 * List<String> strings;
 * void readMap(Map<String, ? extends Object> map);
 * }
 * @author daniel
 *
 */
public class GenericObjectParameter extends GenericParameter {
    private TypeName name;
    
    public GenericObjectParameter(TypeName name) {
        this.name = name;
    }
    
    public GenericObjectParameter() {
        
    }
    
    public String toString() {
        return name.toString();
    };
}
