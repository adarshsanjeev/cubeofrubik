package com.example.improbable.cubeofrubik;
import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;

public class Engine {
    // globals
    static ArrayList<String> moves_list;
    static ArrayList<String> last_scramble;
    static ArrayList<String> f2l_list;
    static ArrayList<Integer> step_moves_list;
    static int solution_length = 0;
    static char[][][] a;

    // creates a 3d list representing a solved cube
    public static char[][][] make_cube() {
        step_moves_list = new ArrayList<>(Arrays.asList(0, 0, 0, 0));
        f2l_list = new ArrayList<>();
        moves_list = new ArrayList<>();
        char[][][] a = {   {{'W', 'W', 'W'},
         					{'W', 'W', 'W'},
         					{'W', 'W', 'W'}}, //Up/white

         				   {{'G', 'G', 'G'},
         					{'G', 'G', 'G'},
         					{'G', 'G', 'G'}}, //front/green

         				   {{'R', 'R', 'R'},
         					{'R', 'R', 'R'},
         					{'R', 'R', 'R'}}, //right/red

         				   {{'O', 'O', 'O'},
         					{'O', 'O', 'O'},
         					{'O', 'O', 'O'}}, //left/orange

         				   {{'Y', 'Y', 'Y'},
         					{'Y', 'Y', 'Y'},
         					{'Y', 'Y', 'Y'}}, //down/yellow

         				   {{'B', 'B', 'B'},
         					{'B', 'B', 'B'},
         					{'B', 'B', 'B'}}}; //back/blue
        return a;
    }

    public static void main() {
        a = make_cube();
        //System.out.println("Before");
        // print_cube();
        // scramble(25);
        // solve();
        // print_cube();
    }

    // prints a string representation of the cube to the interpreter
    public static void print_cube() {
        for (int i=0; i<3; i++) {
            System.out.print("    ");
            for (int j=0; j<3; j++)
                System.out.print(a[5][i][j]);
            System.out.print("\n");
        }
        System.out.print("\n");
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++)
                System.out.print(a[3][i][j]);
            System.out.print(" ");
            for (int j=0; j<3; j++)
                System.out.print(a[0][i][j]);
            System.out.print(" ");
            for (int j=0; j<3; j++)
                System.out.print(a[2][i][j]);
            System.out.print("\n");
        }
        System.out.print("\n");
        for (int i=0; i<3; i++) {
            System.out.print("    ");
            for (int j=0; j<3; j++)
                System.out.print(a[1][i][j]);
            System.out.print("\n");
        }
        System.out.print("\n");
        for (int i=0; i<3; i++) {
            System.out.print("    ");
            for (int j=0; j<3; j++)
                System.out.print(a[4][i][j]);
            System.out.print("\n");
        }
    }

    // simplifies the list of moves and returns a string representation of the moves
    public static String get_moves() throws Exception{
        simplify_moves();
        String s = "";
        for (String i : moves_list) {
            s = s.concat(i+" ");
        }
        s = s.replace("i", "'").substring(0, -1);
        return s;
    }

    // returns a string representation of the last scramble
    public static String get_scramble() {
        String	s = "";
        for (String i: last_scramble) {
            s = s.concat(i+" ");
        }
        s = s.replace("i", "'").substring(0, -1);
        return s;
    }

    //Transforms a given move into the corresponding move after a Y-rotation
    public static String yTransform(String move) throws Exception{
        if (move.charAt(0) == 'U' || move.charAt(0) == 'D')
            return move;
        if (move.charAt(0) == 'F')
            return 'R' + move.substring(1);
        if (move.charAt(0) == 'R')
            return 'B' + move.substring(1);
        if (move.charAt(0) == 'B')
            return 'L' + move.substring(1);
        if (move.charAt(0) == 'L')
            return 'F' + move.substring(1);
        throw new Exception("Invalid move to yTransform: " + move);
    }

    // modifies the global moves list by removing redundancies
    public static void simplify_moves() throws Exception{
        ArrayList<String> new_list = new ArrayList<>();
        String prev_move = "";
        Integer yCount = 0;
        String mv;
        for (String move: moves_list) {
            if (move.equals("Y")) {
                yCount += 1;
                yCount %= 4;
                continue;
            }
            if (move.equals("Yi")) {
                yCount += 3;
                yCount %= 4;
                continue;
            }
            if (move.equals("Y2")) {
                yCount += 2;
                yCount %= 4;
                continue;
            }
            if (yCount > 0)
                for (int i=0; i<yCount; i++)
                    move = yTransform(move);
            if (prev_move.equals("")) {
                prev_move = move;
                new_list.add(move);
                continue;
            }
            if (move.charAt(0) == prev_move.charAt(0)) {
                if (move.length() == 1) {
                    if (prev_move.length() <= 1) {
                        new_list.remove(new_list.size()-1);
                        mv = move.charAt(0)+"2";
                        new_list.add(mv);
                        prev_move = mv;
                        continue;
                    }
                    if (prev_move.charAt(1) == 'i') {
                        new_list.remove(new_list.size()-1);
                        if (new_list.size() > 0)
                            prev_move = new_list.get(new_list.size()-1);
                        else
                            prev_move = "";
                        continue;
                    }
                    if (prev_move.charAt(1) == '2') {
                        new_list.remove(new_list.size()-1);
                        mv = move.charAt(0) + "i";
                        new_list.add(mv);
                        prev_move = mv;
                        continue;
                    }
                }
                if (move.charAt(1) == 'i') {
                    if (prev_move.length() == 1) {
                        new_list.remove(new_list.size()-1);
                        if (new_list.size() > 0)
                            prev_move = new_list.get(new_list.size()-1);
                        else
                            prev_move = "";
                        continue;
                    }
                    if (prev_move.charAt(1) == 'i') {
                        new_list.remove(new_list.size()-1);
                        mv = move.charAt(0) + "2";
                        new_list.add(mv);
                        prev_move = mv;
                        continue;
                    }
                    if (prev_move.charAt(1) == '2') {
                        new_list.remove(new_list.size()-1);
                        mv = Character.toString(move.charAt(0));
                        new_list.add(mv);
                        prev_move = mv;
                        continue;
                    }
                }
                if (move.charAt(1) == '2') {
                    if (prev_move.length() == 1) {
                        new_list.remove(new_list.size()-1);
                        mv = move.charAt(0) + "i";
                        new_list.add(mv);
                        prev_move = mv;
                        continue;
                    }
                    if (prev_move.charAt(1) == 'i') {
                        new_list.remove(new_list.size()-1);
                        mv = Character.toString(move.charAt(0));
                        new_list.add(mv);
                        prev_move = mv;
                        continue;
                    }
                    if (prev_move.charAt(1) == '2') {
                        new_list.remove(new_list.size()-1);
                        if (new_list.size() > 0)
                            prev_move = new_list.get(new_list.size()-1);
                        else
                            prev_move = "";
                        continue;
                    }
                }
            }
            new_list.add(move);
            prev_move = move;
        }
        solution_length = new_list.size();
        moves_list = new_list;
    }


    // sets up the cube to perform a move by rotating that face to the top
    public static void setup(String face) throws Exception{
        face = face.toLowerCase();
        if (face.equals("f"))
            move("X");
        else if (face.equals("r"))
            move("Zi");
        else if (face.equals("l"))
            move("Z");
        else if (face.equals("d"))
            move("X2");
        else if (face.equals("b"))
            move("Xi");
        else
            throw new Exception("Invalid setup; face: " + face);
    }

    // performs the inverse of setup to restore the cube's previous orientation
    public static void undo(String face) throws Exception{
        face = face.toLowerCase();
        if (face.equals("f"))
            move("Xi");
        else if (face.equals("r"))
            move("Z");
        else if (face.equals("l"))
            move("Zi");
        else if (face.equals("d"))
            move("X2");
        else if (face.equals("b"))
            move("X");
        else
            throw new Exception("Invalid undo; face: " + face);
    }

    // Tokenizes a string of moves
    public static void m(String s) throws Exception {
        s = s.replace("'", "i");
        String[] k = s.trim().split("\\s+");
        // global moves_list, solution_length
        solution_length += k.length;
        for (String word : k) {
            moves_list.add(word);
            move(word);
        }
    }

    // performs a move by setting up, performing U moves, and undoing the setup
    public static void move(String mv) throws Exception {
        mv = mv.toLowerCase();
        if (mv.equals( "u"))
            U();
        else if (mv.equals( "u2")) {
            move("U");move("U");
        }
        else if (mv.equals( "ui")) {
            move("U");move("U");move("U");
        }
        else if (mv.equals( "f")) {
            setup("F"); U(); undo("F");
        }
        else if (mv.equals( "f2")) {
            move("F"); move("F");
        }
        else if (mv.equals( "fi")) {
            move("F"); move("F"); move("F");
        }
        else if (mv.equals( "r")) {
            setup("R"); U(); undo("R");
        }
        else if (mv.equals( "r2")) {
            move("R"); move("R");
        }
        else if (mv.equals( "ri")) {
            move("R"); move("R"); move("R");
        }
        else if (mv.equals( "l")) {
            setup("L"); U(); undo("L");
        }
        else if (mv.equals( "l2")) {
            move("L"); move("L");
        }
        else if (mv.equals( "li")) {
            move("L"); move("L"); move("L");
        }
        else if (mv.equals( "b")) {
            setup("B"); U(); undo("B");
        }
        else if (mv.equals( "b2")) {
            move("B"); move("B");
        }
        else if (mv.equals( "bi")) {
            move("B"); move("B"); move("B");
        }
        else if (mv.equals( "d")) {
            setup("D"); U(); undo("D");
        }
        else if (mv.equals( "d2")) {
            move("D"); move("D");
        }
        else if (mv.equals( "di")) {
            move("D"); move("D"); move("D");
        }
        else if (mv.equals( "x")) {
            rotate("X");
        }
        else if (mv.equals( "x2")) {
            move("X"); move("X");
        }
        else if (mv.equals( "xi")) {
            move("X"); move("X"); move("X");
        }
        else if (mv.equals( "y")) {
            rotate("Y");
        }
        else if (mv.equals( "y2")) {
            move("Y"); move("Y");
        }
        else if (mv.equals( "yi")) {
            move("Y"); move("Y"); move("Y");
        }
        else if (mv.equals( "z")) {
            rotate("Z");
        }
        else if (mv.equals( "z2")) {
            move("Z"); move("Z");
        }
        else if (mv.equals( "zi")) {
            move("Z"); move("Z"); move("Z");
        }
        else if (mv.equals( "uw")) {
            move("D"); move("Y");
        }
        else if (mv.equals( "uw2")) {
            move("UW"); move("UW");
        }
        else if (mv.equals( "uwi")) {
            move("UW"); move("UW"); move("UW");
        }
        else if (mv.equals( "m")) {
            move("Li"); move("R"); move("Xi");
        }
        else if (mv.equals( "mi")) {
            move("M"); move("M"); move("M");
        }
        else if (mv.equals( "m2")) {
            move("M"); move("M");
        }
        else if (mv.equals( "rw")) {
            move("L"); move("X");
        }
        else if (mv.equals( "rwi")) {
            move("RW"); move("RW"); move("RW");
        }
        else if (mv.equals( "rw2")) {
            move("RW"); move("RW");
        }
        else if (mv.equals( "fw")) {
            move("Bi"); move("Z");
        }
        else if (mv.equals( "fwi")) {
            move("FW"); move("FW"); move("FW");
        }
        else if (mv.equals( "fw2")) {
            move("FW"); move("FW");
        }
        else if (mv.equals( "lw")) {
            move("R"); move("Xi");
        }
        else if (mv.equals( "lwi")) {
            move("LW"); move("LW"); move("LW");
        }
        else if (mv.equals( "lw2")) {
            move("LW"); move("LW");
        }
        else if (mv.equals( "bw")) {
            move("F"); move("Zi");
        }
        else if (mv.equals( "bwi")) {
            move("BW"); move("BW"); move("BW");
        }
        else if (mv.equals( "bw2")) {
            move("BW"); move("BW");
        }
        else if (mv.equals( "dw")) {
            move("U"); move("Yi");
        }
        else if (mv.equals( "dwi")) {
            move("DW"); move("DW"); move("DW");
        }
        else if (mv.equals( "dw2")) {
            move("DW"); move("DW");
        }
        else
            throw new Exception("Invalid Move: " + mv);
    }

    //rotates the entire cube along a particular axis
    public static void rotate(String axis) throws Exception {
        axis = axis.toLowerCase();
        char[][] temp;
        if (axis.equals("x")) {
            temp = a[0];
            a[0] = a[1];
            a[1] = a[4];
            a[4] = a[5];
            a[5] = temp;
            rotate_face_counterclockwise("L");
            rotate_face_clockwise("R");
        }
        else if(axis.equals("y")) {
            temp = a[1];
            a[1] = a[2];
            a[2] = a[5];
            a[5] = a[3];
            a[3] = temp;
            // after swaps,
            rotate_face_clockwise("L");
            rotate_face_clockwise("F");
            rotate_face_clockwise("R");
            rotate_face_clockwise("B");
            rotate_face_clockwise("U");
            rotate_face_counterclockwise("D");
        }
        else if (axis.equals("z")) {
            temp = a[0];
            a[0] = a[3];
            a[3] = a[4];
            a[4] = a[2];
            a[2] = temp;
            rotate_face_clockwise("L"); rotate_face_clockwise("L");
            rotate_face_clockwise("D"); rotate_face_clockwise("D");
            rotate_face_clockwise("F");
            rotate_face_counterclockwise("B");
        }
        else
            throw new Exception("Invalid rotation: " + axis);
    }

    // performs a U move
    public static void U() throws Exception {

        // rotate U face
        char temp = a[0][0][0];
        a[0][0][0] = a[0][2][0];
        a[0][2][0] = a[0][2][2];
        a[0][2][2] = a[0][0][2];
        a[0][0][2] = temp;
        temp = a[0][0][1];
        a[0][0][1] = a[0][1][0];
        a[0][1][0] = a[0][2][1];
        a[0][2][1] = a[0][1][2];
        a[0][1][2] = temp;

        // rotate others
        temp = a[5][2][0];
        a[5][2][0] = a[3][2][2];
        a[3][2][2] = a[1][0][2];
        a[1][0][2] = a[2][0][0];
        a[2][0][0] = temp;
        temp = a[5][2][1];
        a[5][2][1] = a[3][1][2];
        a[3][1][2] = a[1][0][1];
        a[1][0][1] = a[2][1][0];
        a[2][1][0] = temp;
        temp = a[5][2][2];
        a[5][2][2] = a[3][0][2];
        a[3][0][2] = a[1][0][0];
        a[1][0][0] = a[2][2][0];
        a[2][2][0] = temp;
    }

    // Rotates a particular face counter-clockwise
    public static void rotate_face_counterclockwise(String face) throws Exception{
        rotate_face_clockwise(face);
        rotate_face_clockwise(face);
        rotate_face_clockwise(face);
    }

    // Rotates a particular face clockwise
    public static void rotate_face_clockwise(String face) throws Exception{
        Integer f_id = -1;
        face = face.toLowerCase();
        if (face.equals("u"))
            f_id = 0;
        else if (face.equals("f"))
            f_id = 1;
        else if (face.equals("r"))
            f_id = 2;
        else if (face.equals("l"))
            f_id = 3;
        else if (face.equals("d"))
            f_id = 4;
        else if (face.equals("b"))
            f_id = 5;
        else
            throw new Exception("Invalid face: " + face);
        char temp = a[f_id][0][0];
        a[f_id][0][0] = a[f_id][2][0];
        a[f_id][2][0] = a[f_id][2][2];
        a[f_id][2][2] = a[f_id][0][2];
        a[f_id][0][2] = temp;
        temp = a[f_id][0][1];
        a[f_id][0][1] = a[f_id][1][0];
        a[f_id][1][0] = a[f_id][2][1];
        a[f_id][2][1] = a[f_id][1][2];
        a[f_id][1][2] = temp;
    }

    // Randomly scrambles the cube given a number of moves, or given a list of moves
    public static void scramble (int moves) throws Exception{
        last_scramble = new ArrayList<>();
        moves_list = new ArrayList<>();
        String prevMove = "";
        Random rand = new Random();
        int r;
        String thisMove;
        for (int i=0; i<moves; i++) {
            // print_cube();
            while (true) {
                thisMove = "";
                r =  rand.nextInt(5);
                switch(r) {
                    case 0:
                        thisMove += "U"; break;
                    case 1:
                        thisMove += "F"; break;
                    case 2:
                        thisMove += "R"; break;
                    case 3:
                        thisMove += "L"; break;
                    case 4:
                        thisMove += "D"; break;
                    case 5:
                        thisMove += "B"; break;
                }
                if (thisMove.equals("U") && prevMove != "U" && prevMove != "D")
                    break;
                if (thisMove.equals("F") && prevMove != "F" && prevMove != "B")
                    break;
                if (thisMove.equals("R") && prevMove != "R" && prevMove != "L")
                    break;
                if (thisMove.equals("L") && prevMove != "L" && prevMove != "R")
                    break;
                if (thisMove.equals("D") && prevMove != "D" && prevMove != "U")
                    break;
                if (thisMove.equals("B") && prevMove != "B" && prevMove != "F")
                    break;
            }
            r = rand.nextInt(3);
            switch(r) {
                case 1:
                    move(thisMove+"i");
                    last_scramble.add(thisMove+"i");
                    break;
                case 2:
                    move(thisMove+"2");
                    last_scramble.add(thisMove+"2");
                    break;
                default:
                    move(thisMove);
                    last_scramble.add(thisMove);
            }
            prevMove = thisMove;
        }
    }




	/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$  MODI'S PART $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$*/
	/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$  MODI'S PART $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$*/
	/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$  MODI'S PART $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$*/
	/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$  MODI'S PART $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$*/


    public static void topCross() throws Exception {

        if(a[0][0][1] == a[0][1][0] && a[0][1][0] == a[0][1][2] && a[0][1][2]== a[0][2][1])
        {
            //print("Cross already done, step skipped")
            return;
        }
        //If this is true, we have our cross and we can go onto the next step
        else
        {
            while(a[0][0][1] != 'W' || a[0][1][0] != 'W' || a[0][1][2] != 'W' || a[0][2][1] != 'W')
            {
                if(a[0][1][0] == a[0][1][2])
                {
                    //if we have a horizontal line Just do alg
                    m("F R U Ri Ui Fi");
                    break;
                    //breaking w/o having to recheck while conditions again, this will give us a cross
                }
                else if(a[0][0][1] == a[0][2][1])
                {
                    //if we have a vertical line, do a U then alg
                    m("U F R U Ri Ui Fi");
                    break;
                }
                else if(a[0][0][1] != 'W' && a[0][1][0] != 'W' && a[0][1][2] != 'W' && a[0][2][1] != 'W')
                {
                    //This would mean we have a dot case, so perform
                    m("F U R Ui Ri Fi U F R U Ri Ui Fi");
                    break;
                }
                else if(a[0][1][2] == a[0][2][1] || a[0][0][1] == a[0][1][0])
                {
                    //If we have an L case in the top left or the bottom right, will give us a line
                    m("F R U Ri Ui Fi");
                }
                else
                {
                    //This is we dont have a line, dot, cross, or L in top left or bottom right
                    m("U");
                }
            }
        }
    }



    //returns True if the top is solved
    public static boolean isTopSolved() throws Exception
    {
        //determines if the top of the cube is solved.
        if(a[0][0][0] == a[0][0][1] && a[0][0][0]== a[0][0][2])
        {
            if(a[0][0][0]== a[0][1][0] && a[0][0][0] == a[0][1][1] && a[0][0][0]==a[0][1][2])
            {
                if(a[0][0][0]==a[0][2][0] && a[0][0][0]== a[0][2][1] && a[0][0][0]==a[0][2][2])
                    return true;
            }
        }
        return false;
    }



    //puts a single edge piece in the proper location for the cross
    //Assumes the cross is formed on the bottom and is the yellow face
    //Checks all edges in front/up face, then back-right/left if needed
    public static void putCrossEdge() throws Exception
    {
        for(int i=0;i<3;i++)
        {
            if(i==1)
                m("Ri U R F2");  //bring out back-right edge
            else if(i == 2)
                m("L Ui Li F2"); //bring out back-left edge
            for(int j=0;j<4;j++)
            {
                for(int k=0;k<4;k++)
                {
                    if('Y' == a[4][0][1] || 'Y' == a[1][2][1])
                        return;
                    m("F");
                }
                m("U");
            }
        }
    }


    //Performs the first step of the solution: the cross
    public static void cross() throws Exception
    {
        for(int i=0;i<4;i++)
        {
            putCrossEdge();
            assert ('Y'==a[4][0][1] || 'Y'==a[1][2][1]);
            if(a[1][2][1] == 'Y')
                m("Fi R U Ri F2");   //orient if necessary
            m("Di");
        }

        //permute to correct face: move down face until 2 are lined up,
        //then swap the other 2 if they need to be swapped
        boolean condition = false,fSame=false,rSame=false,bSame=false,lSame=false;
        int cnt;
        while(!condition)
        {
            cnt=0;
            fSame = a[1][1][1] == a[1][2][1];
            if(fSame==true)
                cnt++;
            rSame = a[2][1][1] == a[2][1][2];
            if(rSame==true)
                cnt++;
            bSame = a[5][1][1] == a[5][0][1];
            if(bSame==true)
                cnt++;
            lSame = a[3][1][1] == a[3][1][0];
            if(lSame==true)
                cnt++;
            if(cnt>=2)
                condition=true;

            //condition = (fSame, rSame, bSame, lSame).count(True) >= 2;
            if(!condition)
                m("D");
        }
        cnt=0;
        if(fSame==true)
            cnt++;
        if(rSame==true)
            cnt++;
        if(bSame==true)
            cnt++;
        if(lSame==true)
            cnt++;
        if(cnt == 4)
            return;
        assert (cnt == 2);
        if(!fSame && !bSame)
            m("F2 U2 B2 U2 F2"); //swap front-back
        else if(!rSame && !lSame)
            m("R2 U2 L2 U2 R2"); //swap right-left
        else if(!fSame && !rSame)
            m("F2 Ui R2 U F2"); //swap front-right
        else if(!rSame && !bSame)
            m("R2 Ui B2 U R2"); //swap right-back
        else if(!bSame && !lSame)
            m("B2 Ui L2 U B2"); //swap back-left
        else if(!lSame && !fSame)
            m("L2 Ui F2 U L2"); //swap left-front
        fSame = a[1][1][1] == a[1][2][1];
        rSame = a[2][1][1] == a[2][1][2];
        bSame = a[5][1][1] == a[5][0][1];
        lSame = a[3][1][1] == a[3][1][0];

        assert (fSame==true && rSame==true && bSame==true && lSame==true);
    }

    //This is uses all the f2l algs to solve all the cases possible
    public static void solveFrontSlot() throws Exception
    {
        //This will be F2L, with all 42 cases
        char rmid = a[2][1][1];
        char fmid = a[1][1][1];
        char dmid = a[4][1][1];
        boolean fCorU,rCorU,uCorU,fCorD,rCorD, dCorD,norEdgeFU,norEdgeLU,norEdgeBU;
        boolean norEdgeRU, norEdgeAny,flipEdgeFU,flipEdgeLU ,flipEdgeBU,flipEdgeRU,flipEdgeAny;
        //corner orientations if in U layer, first letter means the direction that the color is facing
        fCorU = a[1][0][2] == dmid && a[0][2][2] == fmid && a[2][2][0] == rmid;
        rCorU = a[2][2][0] == dmid && a[1][0][2] == fmid && a[0][2][2] == rmid;
        uCorU = a[0][2][2] == dmid && a[2][2][0] == fmid && a[1][0][2] == rmid;
        //Corner orientations for correct location in D layer
        fCorD = a[1][2][2] == dmid && a[2][2][2] == fmid && a[4][0][2] == rmid;
        rCorD = a[2][2][2] == dmid && a[4][0][2] == fmid && a[1][2][2] == rmid;
        dCorD = a[4][0][2] == dmid && a[1][2][2] == fmid && a[2][2][2] == rmid; //This is solved spot
        //edge orientations on U layer, normal or flipped version based on F face
        norEdgeFU = a[1][0][1] == fmid && a[0][2][1] == rmid;
        norEdgeLU = a[3][1][2] == fmid && a[0][1][0] == rmid;
        norEdgeBU = a[5][2][1] == fmid && a[0][0][1] == rmid;
        norEdgeRU = a[2][1][0] == fmid && a[0][1][2] == rmid;
        norEdgeAny = norEdgeFU ||norEdgeLU ||norEdgeBU ||norEdgeRU;
        flipEdgeFU = a[0][2][1] == fmid && a[1][0][1] == rmid;
        flipEdgeLU = a[0][1][0] == fmid && a[3][1][2] == rmid;
        flipEdgeBU = a[0][0][1] == fmid && a[5][2][1] == rmid;
        flipEdgeRU = a[0][1][2] == fmid && a[2][1][0] == rmid;
        flipEdgeAny = flipEdgeFU ||flipEdgeLU ||flipEdgeBU ||flipEdgeRU;
        //edge orientations for normal or flipped insertion into slot
        boolean norEdgeInsert = a[1][1][2] == fmid && a[2][2][1] == rmid; //This is solved spot
        boolean flipEdgeInsert = a[2][2][1] == fmid && a[1][1][2] == rmid;
        //these are for if the back right or front left slots are open or not
        boolean backRight = a[4][2][2] == dmid && a[5][1][2] == a[5][0][2] && a[5][1][2] == a[5][1][1] && a[2][0][1] == a[2][0][2] && a[2][0][1]== rmid;
        boolean frontLeft = a[4][0][0] == dmid && a[1][1][0] ==fmid && a[1][2][0] == fmid && a[3][2][0] == a[3][1][1] && a[3][2][1] == a[3][1][1];

        if(dCorD && norEdgeInsert)
            return;
            //Easy Cases
        else if(fCorU && flipEdgeRU) //Case 1
            m("U R Ui Ri");
        else if(rCorU && norEdgeFU) //Case 2
            m("F Ri Fi R");
        else if(fCorU && norEdgeLU) //Case 3
            m("Fi Ui F");
        else if(rCorU && flipEdgeBU) //Case 4
            m("R U Ri");
            //Reposition Edge
        else if(fCorU && flipEdgeBU) //Case 5
            m("F2 Li Ui L U F2");
        else if(rCorU && norEdgeLU) //Case 6
            m("R2 B U Bi Ui R2");
        else if(fCorU && flipEdgeLU) //Case 7
            m("Ui R U2 Ri U2 R Ui Ri");
        else if(rCorU && norEdgeBU) //Case 8
            m("U Fi U2 F Ui F Ri Fi R");
            // Reposition edge && Corner Flip
        else if(fCorU && norEdgeBU) //Case 9
            m("Ui R Ui Ri U Fi Ui F");
        else if(rCorU && flipEdgeLU) //Case 10
        {
            if(!backRight)
                m("Ri U R2 U Ri");
            else
                m("Ui R U Ri U R U Ri");
        }
        else if(fCorU && norEdgeRU) //Case 11
            m("Ui R U2 Ri U Fi Ui F");
        else if(rCorU && flipEdgeFU) // Case 12
        {
            if(!backRight)
                m("Ri U2 R2 U Ri");
            else
                m("Ri U2 R2 U R2 U R");
        }
        else if(fCorU && norEdgeFU) //Case 13
        {
            if(!backRight)
                m("Ri U R Fi Ui F");
            else
                m("U Fi U F Ui Fi Ui F");
        }
        else if(rCorU && flipEdgeRU) //Case 14
            m("Ui R Ui Ri U R U Ri");
            // Split Pair by Going Over
        else if(fCorU && flipEdgeFU) //Case 15
        {
            if(!backRight)
                m("Ui Ri U R Ui R U Ri");
            else if(!frontLeft)
                m("U R Ui Ri D R Ui Ri Di");
            else
                m("U Ri F R Fi U R U Ri");
        }
        else if(rCorU && norEdgeRU) // Case 16
            m("R Ui Ri U2 Fi Ui F");
        else if(uCorU && flipEdgeRU) //Case 17
            m("R U2 Ri Ui R U Ri");
        else if(uCorU && norEdgeFU) // Case 18
            m("Fi U2 F U Fi Ui F");
            // Pair made on side
        else if(uCorU && flipEdgeBU) //Case 19
            m("U R U2 R2 F R Fi");
        else if(uCorU && norEdgeLU) //Case 20
            m("Ui Fi U2 F2 Ri Fi R");
        else if(uCorU && flipEdgeLU) //Case 21
            m("R B U2 Bi Ri");
        else if(uCorU && norEdgeBU) //Case 22
            m("Fi Li U2 L F");
            //Weird Cases
        else if(uCorU && flipEdgeFU) //Case 23
            m("U2 R2 U2 Ri Ui R Ui R2");
        else if(uCorU && norEdgeRU) //Case 24
            m("U Fi Li U L F R U Ri");
            //Corner in Place, edge in the U face (All these cases also have set-up moves in case the edge is in the wrong orientation
        else if(dCorD && flipEdgeAny) //Case 25
        {
            if(flipEdgeBU)
                m("U"); //set-up move
            else if(flipEdgeLU)
                m("U2"); //set-up move
            else if(flipEdgeFU)
                m("Ui"); //set-up move
            if(!backRight)
                m("R2 Ui Ri U R2");
            else
                m("Ri Fi R U R Ui Ri F");
        }
        else if(dCorD && norEdgeAny) //Case 26
        {
            if(norEdgeRU)
                m("U"); //set-up move
            else if(norEdgeBU)
                m("U2"); //set-up move
            else if(norEdgeLU)
                m("Ui"); //set-up move
            m("U R Ui Ri F Ri Fi R");
        }
        else if(fCorD && flipEdgeAny) //Case 27
        {
            if(flipEdgeBU)
                m("U"); //set-up move
            else if(flipEdgeLU)
                m("U2"); //set-up move
            else if(flipEdgeFU)
                m("Ui"); //set-up move
            m("R Ui Ri U R Ui Ri");
        }
        else if(rCorD && norEdgeAny) //Case 28
        {
            if(norEdgeRU)
                m("U"); //set-up move
            else if(norEdgeBU)
                m("U2"); //set-up move
            else if(norEdgeLU)
                m("Ui"); //set-up move
            m("R U Ri Ui F Ri Fi R");
        }
        else if(fCorD && norEdgeAny) //Case 29
        {
            if(norEdgeRU)
                m("U"); //set-up move
            else if(norEdgeBU)
                m("U2"); //set-up move
            else if(norEdgeLU)
                m("Ui"); //set-up move
            m("U2 R Ui Ri Fi Ui F");
        }
        else if(rCorD && flipEdgeAny) //Case 30
        {
            if(flipEdgeBU)
                m("U"); //set-up move
            else if(flipEdgeLU)
                m("U2"); //set-up move
            else if(flipEdgeFU)
                m("Ui"); //set-up move
            m("R U Ri Ui R U Ri");
        }
        //Edge in place, corner in U Face
        else if(uCorU && flipEdgeInsert) // Case 31
            m("R U2 Ri Ui F Ri Fi R");
        else if(uCorU && norEdgeInsert) // Case 32
            m("R2 U R2 U R2 U2 R2");
        else if(fCorU && norEdgeInsert) // Case 33
            m("Ui R Ui Ri U2 R Ui Ri");
        else if(rCorU && norEdgeInsert) // Case 34
            m("Ui R U2 Ri U R U Ri");
        else if(fCorU && flipEdgeInsert) // Case 35
            m("U2 R Ui Ri Ui Fi Ui F");
        else if(rCorU && flipEdgeInsert) // Case 36
            m("U Fi Ui F Ui R U Ri");
            //Edge && Corner in place
            //Case 37 is Lol case, already completed
        else if(dCorD && flipEdgeInsert) //Case 38 (Typical flipped f2l pair case
            m("R2 U2 F R2 Fi U2 Ri U Ri");
        else if(fCorD && norEdgeInsert) // Case 39
            m("R2 U2 Ri Ui R Ui Ri U2 Ri");
        else if(rCorD && norEdgeInsert) // Case 40
            m("R U2 R U Ri U R U2 R2");
        else if(fCorD && flipEdgeInsert) //Case 41
            m("F2 Li Ui L U F Ui F");
        else if(rCorD && flipEdgeInsert) // Case 42
            m("R Ui Ri Fi Li U2 L F");
    }

    //done

    //Returns true if the f2l Corner in FR spot is inserted && oriented correctly
    public static boolean f2lCorner() throws Exception
    {
        return a[4][0][2] == a[4][1][1] && a[1][2][2] == a[1][1][1] && a[2][2][2] == a[2][1][1]; //This is solved spot
    }

    //Returns true if the f2l edge in FR spot is inserted && oriented correctly
    public static boolean f2lEdge() throws Exception
    {
        return a[1][1][2] == a[1][1][1] && a[2][2][1] == a[2][1][1]; //This is solved spot
    }

    //Returns true if the f2l edge && corner are properly inserted && orientated in the FR position
    public static boolean f2lCorrect() throws Exception
    {
        return f2lCorner() && f2lEdge();
    }

    //done

    // returns if the f2l edge is on the top layer at all
    public static boolean f2lEdgeOnTop() throws Exception
    {
        char rmid = a[2][1][1];
        char fmid = a[1][1][1];
        char dmid = a[4][1][1];
        //edge orientations on U layer, normal or flipped version based on F face
        boolean norEdgeFU = a[1][0][1] == fmid && a[0][2][1] == rmid;
        boolean norEdgeLU = a[3][1][2] == fmid && a[0][1][0] == rmid;
        boolean norEdgeBU = a[5][2][1] == fmid && a[0][0][1] == rmid;
        boolean norEdgeRU = a[2][1][0] == fmid && a[0][1][2] == rmid;
        boolean norEdgeAny = norEdgeFU || norEdgeLU || norEdgeBU || norEdgeRU;
        boolean flipEdgeFU = a[0][2][1] == fmid && a[1][0][1] == rmid;
        boolean flipEdgeLU = a[0][1][0] == fmid && a[3][1][2] == rmid;
        boolean flipEdgeBU = a[0][0][1] == fmid && a[5][2][1] == rmid;
        boolean flipEdgeRU = a[0][1][2] == fmid && a[2][1][0] == rmid;
        boolean flipEdgeAny = flipEdgeFU || flipEdgeLU || flipEdgeBU || flipEdgeRU;
        return norEdgeAny || flipEdgeAny;
    }

    //returns true if the f2l edge is inserted. Can be properly orientated, or flipped.
    public static boolean f2lEdgeInserted() throws Exception
    {
        char rmid = a[2][1][1];
        char fmid = a[1][1][1];
        //edge orientations for normal or flipped insertion into slot
        boolean norEdgeInsert = a[1][1][2] == fmid && a[2][2][1] == rmid; //This is solved spot
        boolean flipEdgeInsert = a[2][2][1] == fmid && a[1][1][2] == rmid;
        return norEdgeInsert || flipEdgeInsert;
    }

    //done
    //This is used to determine if the front f2l edge is inserted or not, the parameter is for the requested edge. takes BR, BL, && FL as valid
    public static boolean f2lEdgeInserted2(String p) throws Exception
    {
        char rmid = a[2][1][1];
        char fmid = a[1][1][1];
        //edge orientations for normal or flipped insertion into slot
        boolean norEdgeInsert = a[1][1][2] == fmid && a[2][2][1] == rmid; //This is solved spot
        boolean flipEdgeInsert = a[2][2][1] == fmid && a[1][1][2] == rmid;
        //Edge orientations in comparison to Front &&Right colors
        boolean BR = (a[5][1][2] == fmid && a[2][0][1] == rmid) || (a[5][1][2] == rmid && a[2][0][1] == fmid);
        boolean BL = (a[3][0][1] == fmid && a[5][1][0] == rmid) || (a[3][0][1] == rmid && a[5][1][0] == fmid);
        boolean FL = (a[3][2][1] == fmid && a[1][1][0] == rmid) || (a[3][2][1] == rmid && a[1][1][0] == fmid);

        if(p.equals("BR"))
        {
            if(BR)
                return true;
            else
                return false;
        }
        else if(p.equals("BL"))
        {
            if(BL)
                return true;
            return false;
        }
        else if(p.equals("FL"))
        {
            if(FL)
                return true;
            return false;
        }
        else if(p.equals("FR"))
        {
            if(norEdgeInsert || flipEdgeInsert)
                return true;
        }
        return false;
    }

    //returns true if f2l corner is inserted, doesn't have to be orientated correctly
    public static boolean f2lCornerInserted() throws Exception
    {
        char rmid = a[2][1][1];
        char fmid = a[1][1][1];
        char dmid = a[4][1][1];
        //Corner orientations for correct location in D layer
        boolean fCorD = a[1][2][2] == dmid && a[2][2][2] == fmid && a[4][0][2] == rmid;
        boolean rCorD = a[2][2][2] == dmid && a[4][0][2] == fmid && a[1][2][2] == rmid;
        boolean dCorD = a[4][0][2] == dmid && a[1][2][2] == fmid && a[2][2][2] == rmid; //This is solved spot
        return fCorD || rCorD || dCorD;
    }

    //done

    //Returns true if there is an f2l corner located in the FR orientation
    public static boolean f2lFRCor() throws Exception
    {
        char rmid = a[2][1][1];
        char fmid = a[1][1][1];
        char dmid = a[4][1][1];
        //corner orientations if in U layer, first letter means the direction that the color is facing
        boolean fCorU = a[1][0][2] == dmid && a[0][2][2] == fmid && a[2][2][0] == rmid;
        boolean rCorU = a[2][2][0] == dmid && a[1][0][2] == fmid && a[0][2][2] == rmid;
        boolean uCorU = a[0][2][2] == dmid && a[2][2][0] == fmid && a[1][0][2] == rmid;
        return fCorU || rCorU || uCorU;
    }

    //Returns true if there is an f2l Edge located in the FU position
    public static boolean f2lFUEdge() throws Exception
    {
        char rmid = a[2][1][1];
        char fmid = a[1][1][1];
        boolean norEdgeFU = a[1][0][1] == fmid && a[0][2][1] == rmid;
        boolean flipEdgeFU = a[0][2][1] == fmid && a[1][0][1] == rmid;
        return norEdgeFU || flipEdgeFU;
    }

    //returns true if f2l corner is located on the U layer
    public static boolean f2lCornerOnTop() throws Exception
    {
        boolean wasFound = false;
        for(int i=0;i<4;i++) //Does 4 U moves to find the corner
        {
            if(f2lFRCor())
                wasFound = true;
            m("U");
        }
        return wasFound;
    }

    //Will return the loction of the corner that belongs in the FR spot. Either returns BR, BL, FL, or FR.
    public static String f2lCornerCheck() throws Exception
    {
        String r = "FR";
        int count = 0;
        while(count < 4)
        {
            if(count == 0)
            {
                if(f2lCornerInserted())
                    r = "FR";
            }
            else if(count == 1)
            {
                if(f2lCornerInserted())
                    r = "FL";
            }
            else if(count == 2)
            {
                if(f2lCornerInserted())
                    r = "BL";
            }
            else if(count == 3)
            {
                if(f2lCornerInserted())
                    r = "BR";
            }
            m("D");
            count += 1;
        }
        return r;
    }




	/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$  SAURABH'S PART $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$*/
	/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$  SAURABH'S PART $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$*/
	/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$  SAURABH'S PART $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$*/
	/* $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$  SAURABH'S PART $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$*/



    //Will return the loction of the edge that belongs in the FR spot.
    //Either returns BR, BL, FL, or FR.
    public static String f2lEdgeCheck() throws Exception {
        if(f2lEdgeInserted2("FL")) {
            return "FL";
        }
        else if(f2lEdgeInserted2("BL")) {
            return "BL";
        }
        else if(f2lEdgeInserted2("BR")) {
            return "BR";
        }
        else if(f2lEdgeInserted2("FR")) {
            return "FR";
        } else {
            throw new Exception("f2lEdgeCheck() Exception");
        }
    }

    //This is for the case where the Edge is inserted, but the corner is not
    public static void f2lEdgeNoCorner() throws Exception {
        char topEdgeTop = a[0][2][1];
        char topEdgeFront = a[1][0][1];
        char rmid = a[2][1][1];
        char bmid = a[5][1][1];
        char lmid = a[3][1][1];
        char fmid = a[1][1][1];
        //This is for comparing the front edge to other various edges for advanced algs/lookahead

        boolean BREdge = (topEdgeTop == rmid || topEdgeTop == bmid) && (topEdgeFront == rmid || topEdgeFront == bmid);
        boolean BLEdge = (topEdgeTop == lmid || topEdgeTop == bmid) && (topEdgeFront == lmid || topEdgeFront == bmid);
        boolean FLEdge = (topEdgeTop == fmid || topEdgeTop == lmid) && (topEdgeFront == fmid || topEdgeFront == lmid);
        if(f2lCornerOnTop()) {
            while(true) {
                solveFrontSlot();
                if (f2lCorrect())
                    break;
                m("U");
            }
        }
        else {
            if (f2lCornerCheck().equals("BR")) {
                if (BREdge)
                    m("Ri Ui R U2");
                else
                    m("Ri U R U");
            } else if (f2lCornerCheck().equals("BL")) {
                if (BLEdge)
                    m("L U Li U");
                else
                    m("L Ui Li U2");
            } else if (f2lCornerCheck().equals("FL")) {
                if (FLEdge)
                    m("Li U L Ui");
                else
                    m("Li Ui L");
            }
        }
        solveFrontSlot();

        if (!f2lCorrect())
            throw new Exception("Exception found in f2lEdgeNoCorner()");
    }

    //This is the case for if the corner is inserted, but the edge is not
    public static void f2lCornerNoEdge() throws Exception {
        char topEdgeTop = a[0][2][1];
        char topEdgeFront = a[1][0][1];
        char rmid = a[2][1][1];
        char bmid = a[5][1][1];
        char lmid = a[3][1][1];
        char fmid = a[1][1][1];
        //This is for comparing the front edge to other various edges for advanced algs/lookahead
        boolean BREdge = (topEdgeTop == rmid || topEdgeTop == bmid) && (topEdgeFront == rmid || topEdgeFront == bmid);
        boolean BLEdge = (topEdgeTop == lmid || topEdgeTop == bmid) && (topEdgeFront == lmid || topEdgeFront == bmid);
        boolean FLEdge = (topEdgeTop == fmid || topEdgeTop == lmid) && (topEdgeFront == fmid || topEdgeFront == lmid);
        if(f2lEdgeOnTop()) {
            while(true) {
                solveFrontSlot();
                if(f2lCorrect())
                    break;
                m("U");
            }
        }
        else {
            if(f2lEdgeCheck().equals("BR")) {
                if(BREdge)
                    m("Ri Ui R U2");
                else
                    m("Ri U R U");
            }
            else if(f2lEdgeCheck().equals("BL")) {
                if(BLEdge)
                    m("L U Li U");
                else
                    m("L Ui Li U2");
            }
            else if(f2lEdgeCheck().equals("FL")) {
                if(FLEdge)
                    m("Li U L Ui");
                else
                    m("Li Ui L");
            }
        }
        solveFrontSlot();

        if(!f2lCorrect())
            throw new Exception("Exception found in f2lCornerNoEdge()");
    }


    //this is the case for if the corner is on top, and the edge is not. Neither are inserted properly. Edge must be in another slot.
    public static void f2lCornerTopNoEdge() throws Exception {
        char topEdgeTop = a[0][2][1];
        char topEdgeFront = a[1][0][1];
        char rmid = a[2][1][1];
        char bmid = a[5][1][1];
        char lmid = a[3][1][1];
        char fmid = a[1][1][1];
        //This is for comparing the front edge to other various edges for advanced algs/lookahead
        boolean BREdge = (topEdgeTop == rmid || topEdgeTop == bmid) && (topEdgeFront == rmid || topEdgeFront == bmid);
        boolean BLEdge = (topEdgeTop == lmid || topEdgeTop == bmid) && (topEdgeFront == lmid || topEdgeFront == bmid);
        boolean FLEdge = (topEdgeTop == fmid || topEdgeTop == lmid) && (topEdgeFront == fmid || topEdgeFront == lmid);

        //Turn the top until the corner on the U face is in the proper position
        while(true) {
            if(f2lFRCor())
                break;
            m("U");
        }
        //We will be checking additional edges to choose a more fitting alg for the sake of looking ahead
        if(f2lEdgeCheck().equals("BR")) {
            if (BREdge)
                m("Ri Ui R");
            else
                m("Ri U R");
        }
        else if(f2lEdgeCheck().equals("BL")) {
            if(BLEdge)
                m("U2 L Ui Li");
            else
                m("L Ui Li U");
        }
        else if(f2lEdgeCheck().equals("FL")) {
            if(FLEdge)
                m("U2 Li Ui L U2");
            else
                m("Li Ui L U");
        }
        solveFrontSlot();
        if(!f2lCorrect())
            throw new Exception("Exception found in f2lCornerTopNoEdge()");
    }

    //This is the case for if the edge is on top, and the corner is not. Neither are inserted properly. Corner must be in another slot.
    //The lookahead for this step is comparing the back edge to the slots, rather than the front one like other cases have
    public static void f2lEdgeTopNoCorner() throws Exception {
        char BackEdgeTop = a[0][0][1];
        char BackEdgeBack = a[5][2][1];
        char rmid = a[2][1][1];
        char bmid = a[5][1][1];
        char lmid = a[3][1][1];
        char fmid = a[1][1][1];
        boolean rs1 = (BackEdgeTop == rmid || BackEdgeTop ==bmid);
        boolean	rs2 = (BackEdgeBack == rmid || BackEdgeBack ==bmid);
        //This is for comparing the back edge to other various edges for advanced algs/lookahead
        boolean BREdge = rs1 && rs2;
        boolean BLEdge = ((BackEdgeTop == lmid || BackEdgeTop ==bmid) && (BackEdgeBack == lmid || BackEdgeBack == bmid));
        boolean FLEdge = ((BackEdgeTop == fmid || BackEdgeTop ==lmid) && (BackEdgeBack == fmid || BackEdgeBack == lmid));

        //Turn the top until the corner on the U face is in the proper position
        while(true) {
            if(f2lFUEdge())
                break;
            m("U");
        }
        //We will be checking additional edges to choose a more fitting alg for the sake of looking ahead
        if(f2lCornerCheck().equals("BR")) {
            if(BREdge)
                m("Ri U R U");
            else
                m("Ui Ri U R U");
        }
        else if(f2lCornerCheck().equals("BL")) {
            if(BLEdge)
                m("L Ui Li U2");
            else
                m("U2 L U2 Li");
        }
        else if(f2lCornerCheck().equals("FL")) {
            if(FLEdge)
                m("Li Ui L");
            else
                m("U Li Ui L");
        }
        solveFrontSlot();

        if(!f2lCorrect())
            throw new Exception("Exception found in f2lEdgeTopNoCorner()");
    }

    //This is the case for if the edge or corner are not on top, and not inserted properly. They must both be in other slots.
    public static void f2lNoEdgeOrCorner() throws Exception {
        //The strategy here is to first find the corner and get it out. I will place it in the FR position where it belongs
        //I will then check if I have a case,and if we are all solved.
        //If I don't have it solved at this point, I will have to follow what happens in f2lCornerTopNoEdge()

        char BackEdgeTop = a[0][0][1];
        char BackEdgeBack = a[5][2][1];
        char rmid = a[2][1][1];
        char bmid = a[5][1][1];
        char lmid = a[3][1][1];
        char fmid = a[1][1][1];
        //This is for comparing the back edge to other various edges for advanced algs/lookahead
        boolean BREdge = ((BackEdgeTop == rmid || BackEdgeTop ==bmid) && (BackEdgeBack == rmid || BackEdgeBack == bmid));
        boolean BLEdge = ((BackEdgeTop == lmid || BackEdgeTop ==bmid) && (BackEdgeBack == lmid || BackEdgeBack == bmid));
        boolean FLEdge = ((BackEdgeTop == fmid || BackEdgeTop ==lmid) && (BackEdgeBack == fmid || BackEdgeBack == lmid));

        //We will be checking additional edges to choose a more fitting alg for the sake of looking ahead
        if(f2lCornerCheck().equals("BR")) {
            if(BREdge)
                m("Ri U R U");
            else
                m("Ui Ri U R U");
        }
        else if(f2lCornerCheck().equals("BL")) {
            if(BLEdge)
                m("L Ui Li U2");
            else
                m("U2 L U2 Li");
        }
        else if(f2lCornerCheck().equals("FL")) {
            if(FLEdge)
                m("Li Ui L");
            else
                m("U Li Ui L");
        }
        solveFrontSlot();

        if(f2lCorrect())
            return;
        else
            f2lCornerTopNoEdge();

        if (!f2lCorrect())
            throw new Exception("Exception found in f2lNoEdgeOrCorner()");
    }

    //Will return true if the f2l is completed
    public static boolean isf2lDone() throws Exception {
        boolean rside = a[2][0][1] == a[2][0][2] && a[2][0][2] == a[2][1][1] && a[2][1][1] == a[2][1][2] && a[2][1][2] == a[2][2][1] && a[2][2][1] == a[2][2][2];
        boolean bside = a[5][0][0] == a[5][0][1] && a[5][0][1] == a[5][0][2] && a[5][0][2] == a[5][1][0] && a[5][1][0] == a[5][1][1] && a[5][1][1] == a[5][1][2];
        boolean lside = a[3][0][0] == a[3][0][1] && a[3][0][1] == a[3][1][0] && a[3][1][0] == a[3][1][1] && a[3][1][1] == a[3][2][0] && a[3][2][0] == a[3][2][1];
        boolean fside = a[1][1][0] == a[1][1][1] && a[1][1][1] == a[1][1][2] && a[1][1][2] == a[1][2][0] && a[1][2][0] == a[1][2][1] && a[1][2][1] == a[1][2][2];
        return rside && bside && lside && fside;
    }

    //f2l will solve the first 2 layers, checks for each case, then does a Y move to check the next
    public static void f2l() throws Exception {
        int pairsSolved = 0;
        int uMoves = 0;
        while(pairsSolved < 4) {
            if(!f2lCorrect()) {
                //while not f2lCorrect ():
                while (uMoves < 4) {  //4 moves before checking rare cases
                    solveFrontSlot();
                    if (f2lCorrect()) {
                        pairsSolved += 1;
                        f2l_list.add("Normal Case"); // check this
                        break;
                    } else {
                        f2l_list.add("Scanning"); // check this
                        uMoves += 1;
                        m("U");
                    }
                }
                if (!f2lCorrect()) {
                    if (!f2lCornerInserted() && f2lEdgeInserted()) {
                        f2l_list.add("Rare case 1"); //check this
                        f2lEdgeNoCorner();
                        pairsSolved += 1;
                    }
                    else if(!f2lEdgeInserted() && f2lCornerInserted()) {
                        f2l_list.add("Rare case 2"); // check this
                        f2lCornerNoEdge();
                        pairsSolved += 1;
                    }
                    //At this point, they can 't be inserted, must be in U or other spot
                    else if(!f2lEdgeOnTop() && f2lCornerOnTop()) {
                        f2l_list.add("Rare Case 3"); //check this
                        f2lCornerTopNoEdge();
                        pairsSolved += 1;
                    }
                    else if(f2lEdgeOnTop () && !f2lCornerOnTop()) {
                        f2l_list.add("Rare Case 4"); //check this
                        f2lEdgeTopNoCorner();
                        solveFrontSlot();
                        pairsSolved += 1;
                    }
                    else if(!f2lEdgeOnTop() && !f2lCornerOnTop()) {
                        f2l_list.add("Rare Case 5"); //check this
                        f2lNoEdgeOrCorner();
                        pairsSolved += 1;
                    }
                    else
                        throw new Exception("f2l Impossible Case Exception");
                }
            }
            else
                pairsSolved += 1;
            f2l_list.add("We have "); //check this
            f2l_list.add(String.valueOf(pairsSolved)); //check this
            uMoves = 0;
            m("Y");
        }
        //assert(isf2lDone());  // check this
    }

    public static boolean fish() throws Exception { // check return value also
        int count = 0;
        if( a[0][0][0] ==a[0][1][1])
            count ++;
        if(a[0][0][2]==a[0][1][1])
            count++;
        if(a[0][2][0]==a[0][1][1])
            count++;
        if(a[0][2][2]==a[0][1][1])
            count++;
        if(count == 1)
            return true;
        else
            return false;
        // check this //return [a[0][0][0], a[0][0][2], a[0][2][0], a[0][2][2]].count(a[0][1][1]) == 1;
    }

    public static void sune() throws Exception {
        m("R U Ri U R U2 Ri");
    }

    public static void antisune() throws Exception {
        m("R U2 Ri Ui R Ui Ri");
    }

    public static void getfish() throws Exception {
        for (int i=0;i<4;i++) {
            if(fish())
                return;
            sune();
            if(fish())
                return;
            antisune();
            m("U");
        }
        //assert fish(); // check this
    }

    public static void bOLL() throws Exception {
        getfish();
        if(fish()) {
            while (a[0][0][2] != a[0][1][1]) {
                m("U");
            }
            if (a[1][0][0] == a[0][1][1]) {
                antisune();
            }
            else if (a[5][2][0] == a[0][1][1]) {
                m("U2");
                sune();
            }
            else
                throw new Exception("Something went wrong");
        }
        else
            throw new Exception("Fish not set up");
        //check this //assert isTopSolved()
    }

    public static ArrayList<Boolean> getCornerState() throws Exception {
        boolean corner0 = (a[1][0][0] == a[1][1][1]) && (a[ 3][2][2] == a[3][1][1]);
        boolean corner1 = (a[1][0][2] == a[1][1][1]) && (a[ 2][2][0] == a[2][1][1]);
        boolean corner2 = (a[5][2][2] == a[5][1][1]) && (a[ 2][0][0] == a[2][1][1]);
        boolean corner3 = (a[5][2][0] == a[5][1][1]) && (a[ 3][0][2] == a[3][1][1]);
        ArrayList<Boolean> temp = new ArrayList<Boolean>();
        temp.add(corner0);
        temp.add(corner1);
        temp.add(corner2);
        temp.add(corner3);
        return temp;
        //check this //return [corner0, corner1, corner2, corner3]
    }

    //Does permutation of the top layer corners, orients them properly
    public static void permuteCorners() throws Exception {
        for(int i=0;i<2;i++) {
            for(int j=0;j<4;j++) {
                int num = 0;
                ArrayList<Boolean> temp = getCornerState();
                for(int someVar=0;someVar<4;someVar++){
                    if(temp.get(someVar))
                        num++;
                }
                if(num == 4)
                    return;
                if(num == 1) {
                    int index = 0; //= getCornerState().index(True); //check this
                    for(int someVar=0;someVar<4;someVar++){
                        if(temp.get(someVar))
                            index = someVar;
                    }
                    for(int k=0;k<index;k++)
                        m("Y");
                    if(a[1][0][2] == a[2][1][1])
                        m("R2 B2 R F Ri B2 R Fi R");
                    else
                        m("Ri F Ri B2 R Fi Ri B2 R2");
                    for (int f=0;f<index;f++)
                        m("Yi");
                    return;
                }
                m("U");
            }
            m("R2 B2 R F Ri B2 R Fi R");
        }
    }

    //Does permutation of the top layer edges, must be H, Z or U perms after orientation
    public static void permuteEdges() throws Exception {
        ArrayList<Boolean> temp = getEdgeState();
        if(temp.get(0) && temp.get(1) && temp.get(2) && temp.get(3)) // check this
            return;
        if(a[1][0][1] == a[5][1][1] && a[ 5][2][1] ==a[1][1][1]) // H perm
            m("R2 U2 R U2 R2 U2 R2 U2 R U2 R2");
        else if(a[ 1][0][1] ==a[2][1][1] && a[ 2][1][0] ==a[1][1][1]) //Normal Z perm
            m("U Ri Ui R Ui R U R Ui Ri U R U R2 Ui Ri U");
        else if(a[ 1][0][1] ==a[3][1][1] && a[ 3][1][2] ==a[1][1][1]) //Not oriented Z perm
            m("Ri Ui R Ui R U R Ui Ri U R U R2 Ui Ri U2");
        else {
            int uNum = 0;
            while(true) {
                if(a[5][2][0] == a[5][2][1] && a[5][2][1] == a[5][2][2]) { //solid bar is on back then
                    if(a[3][1][2] == a[1][0][0]) { // means we have to do counterclockwise cycle
                        m("R Ui R U R U R Ui Ri Ui R2");
                        break;
                    }
                    else {
                        m("R2 U R U Ri Ui Ri Ui Ri U Ri");
                        break;
                    }
                }
                else {
                    m("U");
                    uNum += 1;
                }
            }
            for(int x=0;x<uNum;x++)
                m("Ui");
        }
    }

    public static ArrayList<Boolean> getEdgeState() throws Exception  {
        boolean fEdge = a[1][0][1] == a[1][1][1];
        boolean rEdge = a[2][1][0] == a[2][1][1];
        boolean bEdge = a[5][2][1] == a[5][1][1];
        boolean lEdge = a[3][1][2] == a[3][1][1];
        ArrayList<Boolean> temp = new ArrayList<Boolean>();
        temp.add(fEdge);
        temp.add(rEdge);
        temp.add(bEdge);
        temp.add(lEdge);
        return temp;
        // return [fEdge, rEdge, bEdge, lEdge]; // check this
    }

    public static void topCorners() throws Exception  {
        permuteCorners();
        //assert all(getCornerState()); // check this
    }

    public static void topEdges() throws Exception  {
        permuteEdges();
        //assert all(getEdgeState()); //check this
    }

    public static void bPLL() throws Exception  {
        topCorners();
        topEdges();
    }

    public static boolean isSolved() throws Exception {
        boolean uside = ((a[0][0][0] == a[0][0][1]) && (a[0][0][0] == a[0][0][2]) && (a[0][0][0] == a[0][1][0]) && (a[0][0][0] == a[0][1][1]) && (a[0][0][0] == a[0][1][2]) && (a[0][0][0] == a[0][2][0]) && (a[0][0][0] == a[0][2][1]) && (a[0][0][0] == a[0][2][2]));
        boolean fside = ((a[1][0][0] == a[1][0][1]) && (a[1][0][0] == a[1][0][2]) && (a[1][0][0] == a[1][1][0]) && (a[1][0][0] == a[1][1][1]) && (a[1][0][0] == a[1][1][2]) && (a[1][0][0] == a[1][2][0]) && (a[1][0][0] == a[1][2][1]) && (a[1][0][0] == a[1][2][2]));
        boolean rside = ((a[2][0][0] == a[2][0][1]) && (a[2][0][0] == a[2][0][2]) && (a[2][0][0] == a[2][1][0]) && (a[2][0][0] == a[2][1][1]) && (a[2][0][0] == a[2][1][2]) && (a[2][0][0] == a[2][2][0]) && (a[2][0][0] == a[2][2][1]) && (a[2][0][0] == a[2][2][2]));
        boolean lside = ((a[3][0][0] == a[3][0][1]) && (a[3][0][0] == a[3][0][2]) && (a[3][0][0] == a[3][1][0]) && (a[3][0][0] == a[3][1][1]) && (a[3][0][0] == a[3][1][2]) && (a[3][0][0] == a[3][2][0]) && (a[3][0][0] == a[3][2][1]) && (a[3][0][0] == a[3][2][2]));
        boolean dside = ((a[4][0][0] == a[4][0][1]) && (a[4][0][0] == a[4][0][2]) && (a[4][0][0] == a[4][1][0]) && (a[4][0][0] == a[4][1][1]) && (a[4][0][0] == a[4][1][2]) && (a[4][0][0] == a[4][2][0]) && (a[4][0][0] == a[4][2][1]) && (a[4][0][0]== a[4][2][2]));
        boolean bside = ((a[5][0][0] == a[5][0][1]) && (a[5][0][0] == a[5][0][2]) && (a[5][0][0] == a[5][1][0]) && (a[5][0][0] == a[5][1][1]) && (a[5][0][0] == a[5][1][2]) && (a[5][0][0] == a[5][2][0]) && (a[5][0][0] == a[5][2][1]) && (a[5][0][0] == a[5][2][2]));
        return uside && fside && rside && lside && dside && bside;
    }

    public static void solve() throws Exception {
        cross();
        simplify_moves();
        step_moves_list.set(0,solution_length);
        f2l();
        simplify_moves();
        System.out.println("After moves");
        // print_cube();
        step_moves_list.set(1,solution_length - step_moves_list.get(0));
        topCross();
        getfish();
        bOLL();
        simplify_moves();
        step_moves_list.set(2,solution_length - step_moves_list.get(1) - step_moves_list.get(0));
        bPLL();
        simplify_moves();
        step_moves_list.set(3,solution_length - step_moves_list.get(2) - step_moves_list.get(1) - step_moves_list.get(0));
        // assert (isSolved()); // check this
    }


    // Performs solve simulations, will return a list with the number of moves, which one was the best
    // and the scramble used to get the best. The worst, which one was the worst, and the scramble
    // used to get the worst. scimNum is the number of simulations to perform, it is an IntVar()
    public static void simulation(int simNum) throws Exception { // check this
        //global a; // check this
        String bestScram  ; //[]; // check this
        String worstScram ; //[]; // check this
        int best = 200;
        int worst = 0;
        int BestNumber = 0;
        int WorstNumber = 0;
        if(simNum >= 50000) { // check this
            System.out.println("Don't do over 50,000 solves at once");
            return;
        }
        int varlimit = simNum; // check this
        for (int i=0;i<varlimit;i++) {
            a = make_cube();
            step_moves_list = new ArrayList<Integer>(); //[0, 0, 0, 0]; // check this
            for(int someVar=0;someVar<4;someVar++)
                step_moves_list.add(0);
            f2l_list = new ArrayList<String>(); //[]; // check this
            moves_list = new ArrayList<String>();    //[]; //check this
            last_scramble = new ArrayList<String>();  //[]; // check this
            scramble(25);
            solve();
            simplify_moves();
            if(solution_length<best) {
                best = solution_length;
                bestScram = get_scramble();
                BestNumber = i;
            }
            if(solution_length > worst) {
                worst = solution_length;
                worstScram = get_scramble();
                WorstNumber = i;
            }
        }
        //return [best, BestNumber, bestScram, worst, WorstNumber, worstScram]; check this
    }

}
