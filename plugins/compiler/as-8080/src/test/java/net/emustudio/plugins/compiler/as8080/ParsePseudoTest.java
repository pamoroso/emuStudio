package net.emustudio.plugins.compiler.as8080;

import net.emustudio.plugins.compiler.as8080.ast.Label;
import net.emustudio.plugins.compiler.as8080.ast.Node;
import net.emustudio.plugins.compiler.as8080.ast.Program;
import net.emustudio.plugins.compiler.as8080.ast.expr.ExprId;
import net.emustudio.plugins.compiler.as8080.ast.expr.ExprInfix;
import net.emustudio.plugins.compiler.as8080.ast.expr.ExprNumber;
import net.emustudio.plugins.compiler.as8080.ast.instr.InstrExpr;
import net.emustudio.plugins.compiler.as8080.ast.instr.InstrNoArgs;
import net.emustudio.plugins.compiler.as8080.ast.pseudo.*;
import net.emustudio.plugins.compiler.as8080.exceptions.used.SyntaxErrorException;
import org.junit.Test;

import java.util.List;

import static net.emustudio.plugins.compiler.as8080.As8080Lexer.*;
import static net.emustudio.plugins.compiler.as8080.Utils.assertTrees;
import static net.emustudio.plugins.compiler.as8080.Utils.parseProgram;

public class ParsePseudoTest {

    @Test
    public void testConstant() {
        Program program = parseProgram("here equ 0x55");
        assertTrees(new Program()
                .addChild(new PseudoEqu(0, 0, "here")
                    .addChild(new ExprNumber(0, 0, 0x55))),
            program
        );
    }

    @Test
    public void testVariable() {
        Program program = parseProgram("here set 0x55");
        assertTrees(new Program()
                .addChild(new PseudoSet(0, 0, "here")
                    .addChild(new ExprNumber(0, 0, 0x55))),
            program
        );
    }

    @Test
    public void testOrg() {
        Program program = parseProgram("org 55+88");
        assertTrees(new Program()
                .addChild(new PseudoOrg(0, 0)
                    .addChild(new ExprInfix(0, 0, OP_ADD)
                        .addChild(new ExprNumber(0, 0, 55))
                        .addChild(new ExprNumber(0, 0, 88)))),
            program
        );
    }

    @Test
    public void testIf() {
        Program program = parseProgram("if 1\n"
            + "  rrc\n"
            + "  rrc\n"
            + "endif");

        assertTrees(new Program()
                .addChild(new PseudoIf(0, 0)
                    .addChild(new ExprNumber(0, 0, 1))
                    .addChild(new InstrNoArgs(0, 0, OPCODE_RRC))
                    .addChild(new InstrNoArgs(0, 0, OPCODE_RRC))),
            program
        );
    }

    @Test
    public void testIfEmpty() {
        List<String> programs = List.of(
            "if 1\n\n\nendif",
            "if 1\n\nendif",
            "if 1\nendif"
        );

        for (String src : programs) {
            Program program = parseProgram(src);
            Node expected = new Program()
                .addChild(new PseudoIf(0,0)
                    .addChild(new ExprNumber(0,0,1)));
            assertTrees(expected, program);
        }
    }

    @Test(expected = SyntaxErrorException.class)
    public void testIfEndifMustBeOnNewLine() {
        parseProgram("if 1\nrrc\nrrc endif");
    }

    @Test
    public void testTwoLabelsInsideIf() {
        Program program = parseProgram("if 1\n"
            + "  label1:\n"
            + "  label2:\n"
            + "endif");

        assertTrees(new Program()
                .addChild(new PseudoIf(0, 0)
                    .addChild(new ExprNumber(0, 0, 1))
                    .addChild(new Label(0, 0, "label1"))
                    .addChild(new Label(0, 0, "label2"))),
            program
        );
    }

    @Test
    public void testInclude() {
        Program program = parseProgram("include 'filename.asm'");
        assertTrees(
            new Program().addChild(new PseudoInclude(0, 0, "filename.asm")),
            program
        );
    }

    @Test
    public void testMacroDef() {
        Program program = parseProgram("shrt macro param1, param2\n"
            + "  rrc\n"
            + "  heylabel: ani 7Fh\n"
            + "endm\n\n");

        Node expected = new Program()
            .addChild(new PseudoMacroDef(0, 0, "shrt")
                .addChild(new PseudoMacroParameter()
                    .addChild(new ExprId(0, 0, "param1")))
                .addChild(new PseudoMacroParameter()
                    .addChild(new ExprId(0, 0, "param2")))
                .addChild(new InstrNoArgs(0, 0, OPCODE_RRC))
                .addChild(new Label(0, 0, "heylabel")
                    .addChild(new InstrExpr(0, 0, OPCODE_ANI)
                        .addChild(new ExprNumber(0, 0, 0x7F)))));

        assertTrees(expected, program);
    }

    @Test
    public void testMacroDefEmpty() {
        List<String> programs = List.of(
            "shrt macro\n\n\nendm",
            "shrt macro\n\nendm",
            "shrt macro\nendm"
        );

        for (String src : programs) {
            Program program = parseProgram(src);
            Node expected = new Program().addChild(new PseudoMacroDef(0, 0, "shrt"));
            assertTrees(expected, program);
        }
    }

    @Test(expected = SyntaxErrorException.class)
    public void testMacroDefEndmMustBeOnNewLine() {
        parseProgram("shrt macro\nrrc\nrrc endm");
    }

    @Test
    public void testMacroCallNoParams() {
        Program program = parseProgram("shrt");
        Node expected = new Program().addChild(new PseudoMacroCall(0, 0, "shrt"));
        assertTrees(expected, program);
    }

    @Test
    public void testMacroCallWithParams() {
        Program program = parseProgram("shrt param1, 45");

        Node expected = new Program()
            .addChild(new PseudoMacroCall(0, 0, "shrt")
                .addChild(new PseudoMacroArgument()
                    .addChild(new ExprId(0, 0, "param1")))
                .addChild(new PseudoMacroArgument()
                    .addChild(new ExprNumber(0, 0, 45))));

        assertTrees(expected, program);
    }
}
