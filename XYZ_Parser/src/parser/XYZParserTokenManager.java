package parser;
/* Generated By:JavaCC: Do not edit this line. XYZParserTokenManager.java */

import java.io.*;
import java.util.*;

public class XYZParserTokenManager implements XYZParserConstants
{
  public static  java.io.PrintStream debugStream = System.out;
  public static  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private static final int jjStopStringLiteralDfa_0(int pos, long active0)
{
   switch (pos)
   {
      default :
         return -1;
   }
}
private static final int jjStartNfa_0(int pos, long active0)
{
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
}
static private final int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
static private final int jjStartNfaWithStates_0(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_0(state, pos + 1);
}
static private final int jjMoveStringLiteralDfa0_0()
{
   switch(curChar)
   {
      default :
         return jjMoveNfa_0(0, 0);
   }
}
static private final void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
static private final void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
static private final void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}
static private final void jjCheckNAddStates(int start, int end)
{
   do {
      jjCheckNAdd(jjnextStates[start]);
   } while (start++ != end);
}
static private final void jjCheckNAddStates(int start)
{
   jjCheckNAdd(jjnextStates[start]);
   jjCheckNAdd(jjnextStates[start + 1]);
}
static final long[] jjbitVec0 = {
   0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
};
static private final int jjMoveNfa_0(int startState, int curPos)
{
   int[] nextStates;
   int startsAt = 0;
   jjnewStateCnt = 130;
   int i = 1;
   jjstateSet[0] = startState;
   int j, kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 46)
                        kind = 46;
                     jjCheckNAddStates(0, 4);
                  }
                  else if ((0x30002c0200000000L & l) != 0L)
                  {
                     if (kind > 13)
                        kind = 13;
                  }
                  else if ((0x800530000000000L & l) != 0L)
                  {
                     if (kind > 45)
                        kind = 45;
                  }
                  else if (curChar == 47)
                     jjAddStates(5, 7);
                  else if (curChar == 38)
                     jjstateSet[jjnewStateCnt++] = 1;
                  if (curChar == 46)
                     jjCheckNAdd(58);
                  break;
               case 1:
                  if (curChar == 38 && kind > 13)
                     kind = 13;
                  break;
               case 2:
                  if (curChar == 38)
                     jjstateSet[jjnewStateCnt++] = 1;
                  break;
               case 56:
                  if ((0x800530000000000L & l) != 0L && kind > 45)
                     kind = 45;
                  break;
               case 57:
                  if (curChar == 46)
                     jjCheckNAdd(58);
                  break;
               case 58:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 47)
                     kind = 47;
                  jjCheckNAdd(58);
                  break;
               case 60:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 48)
                     kind = 48;
                  jjstateSet[jjnewStateCnt++] = 60;
                  break;
               case 61:
                  if (curChar == 47)
                     jjAddStates(5, 7);
                  break;
               case 62:
                  if (curChar == 47)
                     jjCheckNAddStates(8, 10);
                  break;
               case 63:
                  if ((0xffffffffffffdbffL & l) != 0L)
                     jjCheckNAddStates(8, 10);
                  break;
               case 64:
                  if ((0x2400L & l) != 0L && kind > 52)
                     kind = 52;
                  break;
               case 65:
                  if (curChar == 10 && kind > 52)
                     kind = 52;
                  break;
               case 66:
                  if (curChar == 13)
                     jjstateSet[jjnewStateCnt++] = 65;
                  break;
               case 67:
                  if (curChar == 42)
                     jjCheckNAddStates(11, 13);
                  break;
               case 68:
                  if ((0xfffffbffffffffffL & l) != 0L)
                     jjCheckNAddStates(11, 13);
                  break;
               case 69:
                  if (curChar == 42)
                     jjstateSet[jjnewStateCnt++] = 70;
                  break;
               case 70:
                  if ((0xffff7fffffffffffL & l) != 0L)
                     jjCheckNAddStates(11, 13);
                  break;
               case 71:
                  if (curChar == 47 && kind > 52)
                     kind = 52;
                  break;
               case 72:
               case 77:
                  if (curChar == 42)
                     jjCheckNAdd(71);
                  break;
               case 73:
                  if (curChar == 42)
                     jjCheckNAddStates(14, 16);
                  break;
               case 74:
                  if ((0xfffffbffffffffffL & l) != 0L)
                     jjCheckNAddStates(14, 16);
                  break;
               case 75:
                  if (curChar == 42)
                     jjstateSet[jjnewStateCnt++] = 76;
                  break;
               case 76:
                  if ((0xffff7fffffffffffL & l) != 0L)
                     jjCheckNAddStates(14, 16);
                  break;
               case 78:
                  if (curChar == 42)
                     jjstateSet[jjnewStateCnt++] = 73;
                  break;
               case 99:
                  if (curChar == 46)
                     jjstateSet[jjnewStateCnt++] = 98;
                  break;
               case 103:
                  if (curChar == 46)
                     jjstateSet[jjnewStateCnt++] = 102;
                  break;
               case 124:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 46)
                     kind = 46;
                  jjCheckNAddStates(0, 4);
                  break;
               case 125:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 46)
                     kind = 46;
                  jjCheckNAdd(125);
                  break;
               case 126:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjCheckNAddTwoStates(126, 127);
                  break;
               case 127:
                  if (curChar != 46)
                     break;
                  if (kind > 47)
                     kind = 47;
                  jjCheckNAdd(128);
                  break;
               case 128:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 47)
                     kind = 47;
                  jjCheckNAdd(128);
                  break;
               case 129:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjCheckNAddTwoStates(129, 57);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((0x7fffffe07fffffeL & l) != 0L)
                  {
                     if (kind > 48)
                        kind = 48;
                     jjCheckNAdd(60);
                  }
                  else if ((0x2800000028000000L & l) != 0L)
                  {
                     if (kind > 45)
                        kind = 45;
                  }
                  if (curChar == 116)
                     jjAddStates(17, 18);
                  else if (curChar == 100)
                     jjAddStates(19, 20);
                  else if (curChar == 105)
                     jjAddStates(21, 22);
                  else if (curChar == 83)
                     jjAddStates(23, 24);
                  else if (curChar == 101)
                     jjAddStates(25, 26);
                  else if (curChar == 114)
                     jjstateSet[jjnewStateCnt++] = 54;
                  else if (curChar == 102)
                     jjstateSet[jjnewStateCnt++] = 49;
                  else if (curChar == 98)
                     jjstateSet[jjnewStateCnt++] = 45;
                  else if (curChar == 108)
                     jjstateSet[jjnewStateCnt++] = 39;
                  else if (curChar == 110)
                     jjstateSet[jjnewStateCnt++] = 33;
                  else if (curChar == 119)
                     jjstateSet[jjnewStateCnt++] = 30;
                  else if (curChar == 109)
                     jjstateSet[jjnewStateCnt++] = 25;
                  else if (curChar == 118)
                     jjstateSet[jjnewStateCnt++] = 21;
                  else if (curChar == 115)
                     jjstateSet[jjnewStateCnt++] = 17;
                  else if (curChar == 99)
                     jjstateSet[jjnewStateCnt++] = 12;
                  else if (curChar == 112)
                     jjstateSet[jjnewStateCnt++] = 7;
                  break;
               case 3:
                  if (curChar == 99 && kind > 35)
                     kind = 35;
                  break;
               case 4:
               case 14:
                  if (curChar == 105)
                     jjCheckNAdd(3);
                  break;
               case 5:
                  if (curChar == 108)
                     jjstateSet[jjnewStateCnt++] = 4;
                  break;
               case 6:
                  if (curChar == 98)
                     jjstateSet[jjnewStateCnt++] = 5;
                  break;
               case 7:
                  if (curChar == 117)
                     jjstateSet[jjnewStateCnt++] = 6;
                  break;
               case 8:
                  if (curChar == 112)
                     jjstateSet[jjnewStateCnt++] = 7;
                  break;
               case 9:
                  if (curChar == 115 && kind > 35)
                     kind = 35;
                  break;
               case 10:
                  if (curChar == 115)
                     jjCheckNAdd(9);
                  break;
               case 11:
                  if (curChar == 97)
                     jjstateSet[jjnewStateCnt++] = 10;
                  break;
               case 12:
                  if (curChar == 108)
                     jjstateSet[jjnewStateCnt++] = 11;
                  break;
               case 13:
                  if (curChar == 99)
                     jjstateSet[jjnewStateCnt++] = 12;
                  break;
               case 15:
                  if (curChar == 116)
                     jjstateSet[jjnewStateCnt++] = 14;
                  break;
               case 16:
                  if (curChar == 97)
                     jjstateSet[jjnewStateCnt++] = 15;
                  break;
               case 17:
                  if (curChar == 116)
                     jjstateSet[jjnewStateCnt++] = 16;
                  break;
               case 18:
                  if (curChar == 115)
                     jjstateSet[jjnewStateCnt++] = 17;
                  break;
               case 19:
                  if (curChar == 100 && kind > 35)
                     kind = 35;
                  break;
               case 20:
                  if (curChar == 105)
                     jjstateSet[jjnewStateCnt++] = 19;
                  break;
               case 21:
                  if (curChar == 111)
                     jjstateSet[jjnewStateCnt++] = 20;
                  break;
               case 22:
                  if (curChar == 118)
                     jjstateSet[jjnewStateCnt++] = 21;
                  break;
               case 23:
                  if (curChar == 110 && kind > 35)
                     kind = 35;
                  break;
               case 24:
                  if (curChar == 105)
                     jjCheckNAdd(23);
                  break;
               case 25:
                  if (curChar == 97)
                     jjstateSet[jjnewStateCnt++] = 24;
                  break;
               case 26:
                  if (curChar == 109)
                     jjstateSet[jjnewStateCnt++] = 25;
                  break;
               case 27:
                  if (curChar == 101 && kind > 35)
                     kind = 35;
                  break;
               case 28:
               case 115:
                  if (curChar == 108)
                     jjCheckNAdd(27);
                  break;
               case 29:
                  if (curChar == 105)
                     jjstateSet[jjnewStateCnt++] = 28;
                  break;
               case 30:
                  if (curChar == 104)
                     jjstateSet[jjnewStateCnt++] = 29;
                  break;
               case 31:
                  if (curChar == 119)
                     jjstateSet[jjnewStateCnt++] = 30;
                  break;
               case 32:
                  if (curChar == 119 && kind > 35)
                     kind = 35;
                  break;
               case 33:
                  if (curChar == 101)
                     jjstateSet[jjnewStateCnt++] = 32;
                  break;
               case 34:
                  if (curChar == 110)
                     jjstateSet[jjnewStateCnt++] = 33;
                  break;
               case 35:
                  if (curChar == 104 && kind > 35)
                     kind = 35;
                  break;
               case 36:
                  if (curChar == 116)
                     jjstateSet[jjnewStateCnt++] = 35;
                  break;
               case 37:
                  if (curChar == 103)
                     jjstateSet[jjnewStateCnt++] = 36;
                  break;
               case 38:
                  if (curChar == 110)
                     jjstateSet[jjnewStateCnt++] = 37;
                  break;
               case 39:
                  if (curChar == 101)
                     jjstateSet[jjnewStateCnt++] = 38;
                  break;
               case 40:
                  if (curChar == 108)
                     jjstateSet[jjnewStateCnt++] = 39;
                  break;
               case 41:
                  if (curChar == 97)
                     jjCheckNAdd(23);
                  break;
               case 42:
                  if (curChar == 101)
                     jjstateSet[jjnewStateCnt++] = 41;
                  break;
               case 43:
                  if (curChar == 108)
                     jjstateSet[jjnewStateCnt++] = 42;
                  break;
               case 44:
                  if (curChar == 111)
                     jjstateSet[jjnewStateCnt++] = 43;
                  break;
               case 45:
                  if (curChar == 111)
                     jjstateSet[jjnewStateCnt++] = 44;
                  break;
               case 46:
                  if (curChar == 98)
                     jjstateSet[jjnewStateCnt++] = 45;
                  break;
               case 47:
               case 85:
                  if (curChar == 115)
                     jjCheckNAdd(27);
                  break;
               case 48:
                  if (curChar == 108)
                     jjstateSet[jjnewStateCnt++] = 47;
                  break;
               case 49:
                  if (curChar == 97)
                     jjstateSet[jjnewStateCnt++] = 48;
                  break;
               case 50:
                  if (curChar == 102)
                     jjstateSet[jjnewStateCnt++] = 49;
                  break;
               case 51:
                  if (curChar == 114)
                     jjCheckNAdd(23);
                  break;
               case 52:
                  if (curChar == 117)
                     jjstateSet[jjnewStateCnt++] = 51;
                  break;
               case 53:
                  if (curChar == 116)
                     jjstateSet[jjnewStateCnt++] = 52;
                  break;
               case 54:
                  if (curChar == 101)
                     jjstateSet[jjnewStateCnt++] = 53;
                  break;
               case 55:
                  if (curChar == 114)
                     jjstateSet[jjnewStateCnt++] = 54;
                  break;
               case 56:
                  if ((0x2800000028000000L & l) != 0L && kind > 45)
                     kind = 45;
                  break;
               case 59:
                  if ((0x7fffffe07fffffeL & l) == 0L)
                     break;
                  if (kind > 48)
                     kind = 48;
                  jjCheckNAdd(60);
                  break;
               case 60:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 48)
                     kind = 48;
                  jjCheckNAdd(60);
                  break;
               case 63:
                  jjAddStates(8, 10);
                  break;
               case 68:
               case 70:
                  jjCheckNAddStates(11, 13);
                  break;
               case 74:
               case 76:
                  jjCheckNAddStates(14, 16);
                  break;
               case 79:
                  if (curChar == 101)
                     jjAddStates(25, 26);
                  break;
               case 80:
                  if (curChar == 100)
                     jjCheckNAdd(9);
                  break;
               case 81:
                  if (curChar == 110)
                     jjstateSet[jjnewStateCnt++] = 80;
                  break;
               case 82:
                  if (curChar == 101)
                     jjstateSet[jjnewStateCnt++] = 81;
                  break;
               case 83:
                  if (curChar == 116)
                     jjstateSet[jjnewStateCnt++] = 82;
                  break;
               case 84:
                  if (curChar == 120)
                     jjstateSet[jjnewStateCnt++] = 83;
                  break;
               case 86:
                  if (curChar == 108)
                     jjstateSet[jjnewStateCnt++] = 85;
                  break;
               case 87:
                  if (curChar == 83)
                     jjAddStates(23, 24);
                  break;
               case 88:
                  if (curChar == 103 && kind > 35)
                     kind = 35;
                  break;
               case 89:
                  if (curChar == 110)
                     jjstateSet[jjnewStateCnt++] = 88;
                  break;
               case 90:
                  if (curChar == 105)
                     jjstateSet[jjnewStateCnt++] = 89;
                  break;
               case 91:
                  if (curChar == 114)
                     jjstateSet[jjnewStateCnt++] = 90;
                  break;
               case 92:
                  if (curChar == 116)
                     jjstateSet[jjnewStateCnt++] = 91;
                  break;
               case 93:
                  if (curChar == 108)
                     jjCheckNAdd(23);
                  break;
               case 94:
                  if (curChar == 116)
                     jjstateSet[jjnewStateCnt++] = 93;
                  break;
               case 95:
                  if (curChar == 110)
                     jjstateSet[jjnewStateCnt++] = 94;
                  break;
               case 96:
                  if (curChar == 105)
                     jjstateSet[jjnewStateCnt++] = 95;
                  break;
               case 97:
                  if (curChar == 114)
                     jjstateSet[jjnewStateCnt++] = 96;
                  break;
               case 98:
                  if (curChar == 112)
                     jjstateSet[jjnewStateCnt++] = 97;
                  break;
               case 100:
                  if (curChar == 116)
                     jjstateSet[jjnewStateCnt++] = 99;
                  break;
               case 101:
                  if (curChar == 117)
                     jjstateSet[jjnewStateCnt++] = 100;
                  break;
               case 102:
                  if (curChar == 111)
                     jjstateSet[jjnewStateCnt++] = 101;
                  break;
               case 104:
                  if (curChar == 109)
                     jjstateSet[jjnewStateCnt++] = 103;
                  break;
               case 105:
                  if (curChar == 101)
                     jjstateSet[jjnewStateCnt++] = 104;
                  break;
               case 106:
                  if (curChar == 116)
                     jjstateSet[jjnewStateCnt++] = 105;
                  break;
               case 107:
                  if (curChar == 115)
                     jjstateSet[jjnewStateCnt++] = 106;
                  break;
               case 108:
                  if (curChar == 121)
                     jjstateSet[jjnewStateCnt++] = 107;
                  break;
               case 109:
                  if (curChar == 105)
                     jjAddStates(21, 22);
                  break;
               case 110:
                  if (curChar == 102 && kind > 35)
                     kind = 35;
                  break;
               case 111:
                  if (curChar == 116 && kind > 35)
                     kind = 35;
                  break;
               case 112:
                  if (curChar == 110)
                     jjstateSet[jjnewStateCnt++] = 111;
                  break;
               case 113:
                  if (curChar == 100)
                     jjAddStates(19, 20);
                  break;
               case 114:
                  if (curChar == 111 && kind > 35)
                     kind = 35;
                  break;
               case 116:
                  if (curChar == 98)
                     jjstateSet[jjnewStateCnt++] = 115;
                  break;
               case 117:
                  if (curChar == 117)
                     jjstateSet[jjnewStateCnt++] = 116;
                  break;
               case 118:
                  if (curChar == 111)
                     jjstateSet[jjnewStateCnt++] = 117;
                  break;
               case 119:
                  if (curChar == 116)
                     jjAddStates(17, 18);
                  break;
               case 120:
                  if (curChar == 117)
                     jjCheckNAdd(27);
                  break;
               case 121:
                  if (curChar == 114)
                     jjstateSet[jjnewStateCnt++] = 120;
                  break;
               case 122:
                  if (curChar == 105)
                     jjCheckNAdd(9);
                  break;
               case 123:
                  if (curChar == 104)
                     jjstateSet[jjnewStateCnt++] = 122;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               case 63:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjAddStates(8, 10);
                  break;
               case 68:
               case 70:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjCheckNAddStates(11, 13);
                  break;
               case 74:
               case 76:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjCheckNAddStates(14, 16);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 130 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
static final int[] jjnextStates = {
   125, 126, 127, 129, 57, 62, 67, 78, 63, 64, 66, 68, 69, 72, 74, 75, 
   77, 121, 123, 114, 118, 110, 112, 92, 108, 84, 86, 
};
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, null, null, null, null, null, null, null, null, 
null, null, null, null, null, null, null, null, null, null, null, null, null, null, 
null, null, null, null, null, null, null, null, null, null, null, null, null, null, 
null, null, null, null, null, null, null, null, null, null, null, null, };
public static final String[] lexStateNames = {
   "DEFAULT", 
};
static final long[] jjtoToken = {
   0x11e00800002001L, 
};
static final long[] jjtoSkip = {
   0x3eL, 
};
static protected SimpleCharStream input_stream;
static private final int[] jjrounds = new int[130];
static private final int[] jjstateSet = new int[260];
static protected char curChar;
public XYZParserTokenManager(SimpleCharStream stream){
   if (input_stream != null)
      throw new TokenMgrError("ERROR: Second call to constructor of static lexer. You must use ReInit() to initialize the static variables.", TokenMgrError.STATIC_LEXER_ERROR);
   input_stream = stream;
}
public XYZParserTokenManager(SimpleCharStream stream, int lexState){
   this(stream);
   SwitchTo(lexState);
}
static public void ReInit(SimpleCharStream stream)
{
   jjmatchedPos = jjnewStateCnt = 0;
   curLexState = defaultLexState;
   input_stream = stream;
   ReInitRounds();
}
static private final void ReInitRounds()
{
   int i;
   jjround = 0x80000001;
   for (i = 130; i-- > 0;)
      jjrounds[i] = 0x80000000;
}
static public void ReInit(SimpleCharStream stream, int lexState)
{
   ReInit(stream);
   SwitchTo(lexState);
}
static public void SwitchTo(int lexState)
{
   if (lexState >= 1 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
   else
      curLexState = lexState;
}

static protected Token jjFillToken()
{
   Token t = Token.newToken(jjmatchedKind);
   t.kind = jjmatchedKind;
   String im = jjstrLiteralImages[jjmatchedKind];
   t.image = (im == null) ? input_stream.GetImage() : im;
   t.beginLine = input_stream.getBeginLine();
   t.beginColumn = input_stream.getBeginColumn();
   t.endLine = input_stream.getEndLine();
   t.endColumn = input_stream.getEndColumn();
   t.beginOffset = input_stream.bufpos - t.image.length() + 1;
   t.endOffset = input_stream.bufpos;
   return t;
}

static int curLexState = 0;
static int defaultLexState = 0;
static int jjnewStateCnt;
static int jjround;
static int jjmatchedPos;
static int jjmatchedKind;

public static Token getNextToken() 
{
  int kind;
  Token specialToken = null;
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {   
   try   
   {     
      curChar = input_stream.BeginToken();
   }     
   catch(java.io.IOException e)
   {        
      jjmatchedKind = 0;
      matchedToken = jjFillToken();
      return matchedToken;
   }

   try { input_stream.backup(0);
      while (curChar <= 32 && (0x100003600L & (1L << curChar)) != 0L)
         curChar = input_stream.BeginToken();
   }
   catch (java.io.IOException e1) { continue EOFLoop; }
   jjmatchedKind = 0x7fffffff;
   jjmatchedPos = 0;
   curPos = jjMoveStringLiteralDfa0_0();
   if (jjmatchedKind != 0x7fffffff)
   {
      if (jjmatchedPos + 1 < curPos)
         input_stream.backup(curPos - jjmatchedPos - 1);
      if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
      {
         matchedToken = jjFillToken();
         return matchedToken;
      }
      else
      {
         continue EOFLoop;
      }
   }
   int error_line = input_stream.getEndLine();
   int error_column = input_stream.getEndColumn();
   String error_after = null;
   boolean EOFSeen = false;
   try { input_stream.readChar(); input_stream.backup(1); }
   catch (java.io.IOException e1) {
      EOFSeen = true;
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
      if (curChar == '\n' || curChar == '\r') {
         error_line++;
         error_column = 0;
      }
      else
         error_column++;
   }
   if (!EOFSeen) {
      input_stream.backup(1);
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
   }
   TokenMgrError.LexicalError(EOFSeen, curLexState, error_line, error_column, error_after, curChar);
   if (EOFSeen) {
	   return Token.newToken(0); //EOF = 0 
   }
   try {
	curChar = input_stream.readChar();
   } catch (IOException e) {
	   e.printStackTrace();
   }
   //throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
  }
}

}
