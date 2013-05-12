package net.zetaeta.tools.java.compiler.ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Joiner;

public class Method extends Member {
    protected List<ParameterDeclaration> parameters;
    protected List<GenericTypeParameter> genericParams;
    protected List<TypeName> exceptions;
    
    
    public Method(String name, TypeName returns, Modifiers mods, List<Annotation> annot, List<GenericTypeParameter> genParams, List<ParameterDeclaration> params, List<TypeName> exceptions) {
        super(name, returns, mods, annot);
        parameters = params;
        genericParams = genParams;
        this.exceptions = exceptions;
    }
    
    @Override
    protected Map<?, ?> getAttributes() {
        Map<Object, Object> m = new HashMap<>();
        m.putAll(super.getAttributes());
        if (exceptions != null) {
            m.put("Exceptions", Joiner.on(", ").join(exceptions));
        }
        return m;
    }
    
    @Override
    protected List<TreeNode> getChildList() {
        List<TreeNode> list = new ArrayList<>();
        list.addAll(super.getChildList());
        if (genericParams != null) {
            list.addAll(genericParams);
        }
        list.addAll(parameters);
        return list;
    }
}
