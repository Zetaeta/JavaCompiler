package net.zetaeta.tools.java.compiler.ast;

import java.util.List;

public class Method extends Member {
    protected List<ParameterDeclaration> parameters;
    protected Block body;
}
