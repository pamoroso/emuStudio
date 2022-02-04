parser grammar AsZ80Parser;

options {
   tokenVocab = AsZ80Lexer;
}

rStart:
 EOL* rLine? (EOL+ rLine)* EOL* EOF
 ;

rLine:
  label=ID_LABEL? EOL* statement=rStatement
  | label=ID_LABEL;

rStatement:
  instr=rInstruction
  | pseudo=rPseudoCode
  | data=rData
  ;

rInstruction:
  r8bitInstruction                                                                    # instr8bit
  | opcode=OPCODE_MVI reg=rRegister SEP_COMMA expr=rExpression                        # instrRegExpr
  | opcode=OPCODE_LXI regpair=(REG_B|REG_D|REG_H|REG_SP) SEP_COMMA expr=rExpression   # instrRegPairExpr
  | opcode=OPCODE_LDA expr=rExpression                                                # instrExpr
  | opcode=OPCODE_STA expr=rExpression                                                # instrExpr
  | opcode=OPCODE_LHLD expr=rExpression                                               # instrExpr
  | opcode=OPCODE_SHLD expr=rExpression                                               # instrExpr
  | opcode=OPCODE_ADI expr=rExpression                                                # instrExpr
  | opcode=OPCODE_ACI expr=rExpression                                                # instrExpr
  | opcode=OPCODE_SUI expr=rExpression                                                # instrExpr
  | opcode=OPCODE_SBI expr=rExpression                                                # instrExpr
  | opcode=OPCODE_ANI expr=rExpression                                                # instrExpr
  | opcode=OPCODE_ORI expr=rExpression                                                # instrExpr
  | opcode=OPCODE_XRI expr=rExpression                                                # instrExpr
  | opcode=OPCODE_CPI expr=rExpression                                                # instrExpr
  | opcode=OPCODE_JMP expr=rExpression                                                # instrExpr
  | opcode=OPCODE_JC expr=rExpression                                                 # instrExpr
  | opcode=OPCODE_JNC expr=rExpression                                                # instrExpr
  | opcode=OPCODE_JZ expr=rExpression                                                 # instrExpr
  | opcode=OPCODE_JNZ expr=rExpression                                                # instrExpr
  | opcode=OPCODE_JM expr=rExpression                                                 # instrExpr
  | opcode=OPCODE_JP expr=rExpression                                                 # instrExpr
  | opcode=OPCODE_JPE expr=rExpression                                                # instrExpr
  | opcode=OPCODE_JPO expr=rExpression                                                # instrExpr
  | opcode=OPCODE_CALL expr=rExpression                                               # instrExpr
  | opcode=OPCODE_CC expr=rExpression                                                 # instrExpr
  | opcode=OPCODE_CNC expr=rExpression                                                # instrExpr
  | opcode=OPCODE_CZ expr=rExpression                                                 # instrExpr
  | opcode=OPCODE_CNZ expr=rExpression                                                # instrExpr
  | opcode=OPCODE_CM expr=rExpression                                                 # instrExpr
  | opcode=OPCODE_CP expr=rExpression                                                 # instrExpr
  | opcode=OPCODE_CPE expr=rExpression                                                # instrExpr
  | opcode=OPCODE_CPO expr=rExpression                                                # instrExpr
  | opcode=OPCODE_IN expr=rExpression                                                 # instrExpr
  | opcode=OPCODE_OUT expr=rExpression                                                # instrExpr
  ;

r8bitInstruction:
   opcode=OPCODE_NOP                                                                  # instrNoArgs
  | opcode=OPCODE_INC regpair=(REG_BC|REG_DE|REG_HL|REG_SP)                           # instrRegPair
  | opcode=OPCODE_INC reg=rRegister                                                   # instrReg
  | opcode=OPCODE_DEC reg=rRegister                                                   # instrReg
  | opcode=OPCODE_RLCA                                                                # instrNoArgs
  | opcode=OPCODE_EX src=REG_AF SEP_COMMA dst=REG_AFF                                 # instrRegPairRegPair
  | opcode=OPCODE_EX src=REG_DE SEP_COMMA dst=REG_HL                                  # instrRegPairRegPair
  | opcode=OPCODE_EX SEP_LPAR src=REG_SP SEP_RPAR SEP_COMMA dst=REG_HL                # instrRegPairRegPair
  | opcode=OPCODE_ADD REG_HL SEP_COMMA regpair=(REG_BC|REG_DE|REG_HL|REG_SP)          # instrRegPair
  | opcode=OPCODE_LD REG_A SEP_COMMA SEP_LPAR regpair=(REG_BC|REG_DE) SEP_RPAR        # instrRegPair
  | opcode=OPCODE_DEC regpair=(REG_BC|REG_DE|REG_HL|REG_SP)                           # instrRegPair
  | opcode=OPCODE_RRCA                                                                # instrNoArgs
  | opcode=OPCODE_RLA                                                                 # instrNoArgs
  | opcode=OPCODE_RRA                                                                 # instrNoArgs
  | opcode=OPCODE_DAA                                                                 # instrNoArgs
  | opcode=OPCODE_CPL                                                                 # instrNoArgs
  | opcode=OPCODE_INC SEP_LPAR regpairM=REG_HL SEP_RPAR                               # instrRegPair
  | opcode=OPCODE_DEC SEP_LPAR regpairM=REG_HL SEP_RPAR                               # instrRegPair
  | opcode=OPCODE_SCF                                                                 # instrNoArgs
  | opcode=OPCODE_CCF                                                                 # instrNoArgs
  | opcode=OPCODE_LD dst=rRegister SEP_COMMA src=rRegister                            # instrRegReg
  | opcode=OPCODE_HALT                                                                # instrNoArgs
  | opcode=OPCODE_ADD REG_A SEP_COMMA reg=rRegister                                   # instrReg
  | opcode=OPCODE_ADC REG_A SEP_COMMA reg=rRegister                                   # instrReg
  | opcode=OPCODE_SUB reg=rRegister                                                   # instrReg
  | opcode=OPCODE_SBC REG_A SEP_COMMA reg=rRegister                                   # instrReg
  | opcode=OPCODE_AND reg=rRegister                                                   # instrReg
  | opcode=OPCODE_XOR reg=rRegister                                                   # instrReg
  | opcode=OPCODE_OR reg=rRegister                                                    # instrReg
  | opcode=OPCODE_CP reg=rRegister                                                    # instrReg
  | opcode=OPCODE_RET cond=(COND_C|COND_NC|COND_Z|COND_NZ|COND_M|COND_P|COND_PE|COND_PO)? # instrCond
  | opcode=OPCODE_POP regpair=(REG_BC|REG_DE|REG_HL|REG_AF)                           # instrRegPair
  | opcode=OPCODE_PUSH regpair=(REG_BC|REG_DE|REG_HL|REG_AF)                          # instrRegPair
  | opcode=OPCODE_RST expr=rExpression                                                # instr8bitExpr
  | opcode=OPCODE_EXX                                                                 # instrNoArgs
  | opcode=OPCODE_JP SEP_LPAR regpairM=REG_HL SEP_RPAR                                # instrRegPair
  | opcode=OPCODE_DI                                                                  # instrNoArgs
  | opcode=OPCODE_LD dst=REG_SP SEP_COMMA src=REG_HL                                  # instrRegPairRegPair
  | opcode=OPCODE_EI                                                                  # instrNoArgs
  ;

rRegister:
  r=REG_A
  | r=REG_B
  | r=REG_C
  | r=REG_D
  | r=REG_E
  | r=REG_H
  | r=REG_L
  | SEP_LPAR r=REG_HL SEP_RPAR
  ;




rPseudoCode:
  PREP_ORG expr=rExpression                                                                # pseudoOrg
  | id=ID_IDENTIFIER PREP_EQU expr=rExpression                                             # pseudoEqu
  | id=ID_IDENTIFIER PREP_SET expr=rExpression                                             # pseudoSet
  | PREP_IF expr=rExpression EOL (rLine EOL)* EOL* PREP_ENDIF                              # pseudoIf
  | id=ID_IDENTIFIER PREP_MACRO params=rMacroParameters? EOL (rLine EOL)* EOL* PREP_ENDM   # pseudoMacroDef
  | id=ID_IDENTIFIER args=rMacroArguments?                                                 # pseudoMacroCall
  | PREP_INCLUDE filename=(LIT_STRING_1|LIT_STRING_2)                                      # pseudoInclude
  ;

rMacroParameters:
  ID_IDENTIFIER (SEP_COMMA ID_IDENTIFIER)*
  ;

rMacroArguments:
  rExpression (SEP_COMMA rExpression)*
  ;

rData:
  PREP_DB rDBdata (SEP_COMMA rDBdata)*    # dataDB
  | PREP_DW rDWdata (SEP_COMMA rDWdata)*  # dataDW
  | PREP_DS data=rExpression              # dataDS
  ;

rDBdata:
  expr=rExpression
  | instr=r8bitInstruction
  ;

rDWdata:
  expr=rExpression
  ;

rExpression:
 <assoc=right> unaryop=(OP_ADD|OP_SUBTRACT|OP_NOT) expr=rExpression                     # exprUnary
 | <assoc=left> expr1=rExpression op=(OP_MULTIPLY|OP_DIVIDE|OP_MOD) expr2=rExpression   # exprInfix
 | <assoc=left> expr1=rExpression op=(OP_ADD|OP_SUBTRACT) expr2=rExpression             # exprInfix
 | <assoc=left> expr1=rExpression op=(OP_SHL|OP_SHR) expr2=rExpression                  # exprInfix
 | <assoc=left> expr1=rExpression op=OP_AND expr2=rExpression                           # exprInfix
 | <assoc=left> expr1=rExpression op=OP_XOR expr2=rExpression                           # exprInfix
 | <assoc=left> expr1=rExpression op=OP_OR expr2=rExpression                            # exprInfix
 | <assoc=right> expr1=rExpression op=OP_EQUAL expr2=rExpression                        # exprInfix
 | SEP_LPAR expr=rExpression SEP_RPAR                                                   # exprParens
 | num=LIT_NUMBER                                                                       # exprDec
 | num=LIT_HEXNUMBER_1                                                                  # exprHex1
 | num=LIT_HEXNUMBER_2                                                                  # exprHex2
 | num=LIT_OCTNUMBER                                                                    # exprOct
 | num=LIT_BINNUMBER                                                                    # exprBin
 | PREP_ADDR                                                                            # exprCurrentAddress
 | id=ID_IDENTIFIER                                                                     # exprId
 | str=(LIT_STRING_1|LIT_STRING_2)                                                      # exprString
 ;
