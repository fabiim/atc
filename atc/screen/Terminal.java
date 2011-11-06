package atc.screen;

import java.awt.*;
import java.awt.event.*;
import java.util.Collections;

/** Terminal provides a simple color VT-52 terminal component.
  *
  * This version extends Canvas, so it will be a heavyweight component and
  * work correctly in AWT; change it to extend Component if you need a
  * lightweight component for Swing.  The class is thread-safe; while this is
  * not quite as fast, it simplifies use.  See the main() method for an example
  * of use.
  *
  * Most of the ANSI VT-52 escapes are supported, as well as the CP/M-86
  * extensions.  VT-52 is considerably easier to implement and use than
  * ANSI/VT-100.
  *
  * <pre>
  * Special Characters
  * DEC  Name  Meaning
  * ---  ----  -------
  *   7  BEL   Ring the bell.
  *   8  BS    Backspace, wrap to previous line if wrap is on.
  *   9  HT    Tab, wrap to next line if wrap is on.
  *  10  LF    Newline, cursor to start of next line, scrolls if scroll is on.
  *  11  VT    Vertical Tab, cursor down one line, scrolls if scroll is on.
  *  12  FF    Form Feed, cursor down one line, scrolls if scroll is on.
  *  13  CR    Carriage Return, does nothing because Java newline is LF.
  *  24  CAN   Cancel escape sequence.
  *  26  SUB   Cancel escape sequence.
  *  27  ESC   Start escape sequence.
  * 127  DEL   Delete character at cursor, pulls characters from right.
  * 
  * Escape Sequences
  * ----------------
  * ESC A (Cursor Up)
  *     Moves cursor up one line, if not already on first line.
  * ESC B (Cursor Down)
  *     Moves cursor down one line, if not already on last line.
  * ESC C (Cursor Forward)
  *     Moves cursor right one char, if not already at end of line.
  * ESC D (Cursor Backward)
  *     Moves cursor left one char, if not already at start of line.
  * ESC E (Clear Screen)
  *     Erases all chars on screen and moves cursor to 0,0.
  * ESC H (Home Cursor)
  *     Moves cursor to 0,0.
  * ESC I (Reverse Index)
  *     Moves cursor up one line, scrolls up if on first line and scroll is on.
  * ESC J (Erase to End of Page)
  *     Erases all chars from cursor to end of screen.
  * ESC K (Clear to End of Line)
  *     Erases all chars from cursor to end of line.
  * ESC L (Insert Line)
  *     Moves current and following lines down one line, leaving a blank line
  *     under cursor, moves cursor to start of line.
  * ESC M (Delete Line)
  *     Deletes the current line, moves following lines up one line, leaves last
  *     line blank, and moves cursor to start of line.
  * ESC N (Delete Character)
  *     Same as DEL character.
  * ESC Y <i>R</i> <i>C</i> (Position Cursor)
  *     R and C specify the row (y-coord) and column (x-coord) to move the
  *     cursor to; subtract 32 from the ASCII value of R and C to get their
  *     value.  Coordinates are numbered 0 up.
  * ESC b <i>X</i> (Set Foreground Color)
  *     X specifies the foreground color.  X should be @ or A-O:
  *      value X    color name      hex color
  *      ----- -    ----------      ---------
  *          0 @    WHITE           #ffffff
  *          1 A    RED             #ff0000
  *          2 B    GREEN           #00cc00
  *          3 C    BROWN           #996600
  *          4 D    BLUE            #0000ff
  *          5 E    DARK_GREEN      #009900
  *          6 F    DARK_BLUE       #000099
  *          7 G    LIGHT_GREY      #999999
  *          8 H    DARK_GREY       #666666
  *          9 I    CYAN            #00ffff
  *         10 J    DARK_CYAN       #009999
  *         11 K    DARK_YELLOW     #999900
  *         12 L    MAGENTA         #ff00ff
  *         13 M    YELLOW          #ffff00
  *         14 N    PURPLE          #990099
  *         15 O    BLACK           #000000
  * ESC c <i>X</i> (Set Background Color)
  *     X specifies the foreground color.  X should be @ or A-O (see ESC b).
  * ESC d (Erase from Beginning of Display)
  *     Erases all chars from start of screen to cursor.
  * ESC j (Save Cursor Position)
  *     The current cursor position is saved.
  * ESC k (Restore Cursor Position)
  *     The cursor is moved to the saved cursor position, or 0,0 if no cursor
  *     position has been saved yet.
  * ESC l (Erase Entire Line)
  *     Erases the current line and moves the cursor to the start of the line.
  * ESC m (Enable Cursor)
  *     The cursor block is made visible.
  * ESC n (Disable Cursor)
  *     The cursor block is made invisible.
  * ESC o (Erase from Beginning of Line)
  *     Erases from the beginning of the current line to the cursor.
  * ESC p (Enable Reverse Video)
  *     Enables reverse video, so all characters drawn or erased use the
  *     foreground for background and background for foreground.
  * ESC q (Disable Reverse Video)
  *     Disables reverse video.
  * ESC r (Enable Underline)
  *     All drawn characters will be underlined.
  * ESC u (Disable Underline)
  *     Disables underlining.
  * ESC v (Enable Wrapping)
  *     If characters would move the cursor past the end of the line, the cursor
  *     moves onto the start of the next line.
  * ESC w (Disable Wrapping)
  *     If characters would move the cursor past the end of the line, the cursor
  *     remains at the end of the line.
  * </pre>
  *
  * <pre>
  * Unimplemented features:
  *
  * ESC 0 (Disable Status Line)
  *     Allows use of all lines of the screen.
  * ESC 1 (Enable Status Line)
  *     Reserves the bottom line of the screen for status messages.
  * ESC a <i>H</i> (Select Screen Mode)
  *     Implementation-specific screen size.
  * ESC s (Enable Flashing)
  *     All drawn characters will flash on and off.
  * ESC t (Disable Flashing)
  *     Disables flashing.
  * ESC x (Enable 80X25 Mode)
  *     Changes to 80x25 display mode, remembers previous mode.
  * ESC y (Disable 80X25 Mode)
  *     Changes from 80x25 display mode to previous mode.
  * ESC &lt; (ANSI Mode)
  *     Switch to ANSI/VT-102 mode.
  * ESC = (Enter Application Keypad Mode)
  *     Keypad generates sequences used by applications program.
  * ESC &gt; (Exit Application Keypad Mode)
  *     Keypad generates numeric sequences.
  * ESC F (Enter Graphics Mode)
  *     Selects the special characters and line drawing set.
  * ESC G (Exit Graphics Mode)
  *     Selects the standard character set.
  * ESC ^ (Enable Auto Print)
  *     Prints the current line whenever you move off with linefeed, form feed,
  *     vertical tab, or wrapping.
  * ESC _ (Disable Auto Print)
  *     Turns off auto-print.
  * ESC W (Enable Print Controller)
  *     Prints all received characters without displaying them.
  * ESC X (Disable Print Controller)
  *     Turns off printing.
  * ESC V (Print Cursor Line)
  *     Prints current line.
  * ESC ] (Print Screen)
  *     Prints entire screen.
  * ESC Z (Identify)
  *     If serial line terminal, respond with ESC / Z.
  * </pre>
  *
  * <p>Copyright &copy; 2005 by Mark Hughes (kamikaze@kuoi.asui.uidaho.edu)
  * <br />You may use, modify, and distribute this code freely as long as this
  * header remains attached, and credit is given in the documentation.
  * <br />All other rights reserved.
  * </p>
  */
public class Terminal extends Canvas
{
static final long serialVersionUID = 5479170142892558288L;

public int WIDTH = 80, HEIGHT = 25;

public static final Color
    WHITE       = new Color(0xffffff),
    RED         = new Color(0xff0000),
    GREEN       = new Color(0x00cc00),
    BROWN       = new Color(0x996600),
    BLUE        = new Color(0x0000ff),
    DARK_GREEN  = new Color(0x009900),
    DARK_BLUE   = new Color(0x000099),
    LIGHT_GREY  = new Color(0x999999),
    DARK_GREY   = new Color(0x666666),
    CYAN        = new Color(0x00ffff),
    DARK_CYAN   = new Color(0x009999),
    DARK_YELLOW = new Color(0x999900),
    MAGENTA     = new Color(0xff00ff),
    YELLOW      = new Color(0xffff00),
    PURPLE      = new Color(0x990099),
    BLACK       = new Color(0x000000);

private static final Color[] COLORS = {
        WHITE           ,  // 0 @
        RED             ,  // 1 A
        GREEN           ,  // 2 B
        BROWN           ,  // 3 C
        BLUE            ,  // 4 D
        DARK_GREEN      ,  // 5 E
        DARK_BLUE       ,  // 6 F
        LIGHT_GREY      ,  // 7 G
        DARK_GREY       ,  // 8 H
        CYAN            ,  // 9 I
        DARK_CYAN       ,  // 10 J
        DARK_YELLOW     ,  // 11 K
        MAGENTA         ,  // 12 L
        YELLOW          ,  // 13 M
        PURPLE          ,  // 14 N
        BLACK           ,  // 15 O
    };

/** Enables underlining. */
public static final int EFFECTS_UNDERLINE = 1;

private static final int
    ESC_NORMAL  = 0,
    ESC_ESCAPE  = 1,
    ESC_POS_Y   = 2,
    ESC_POS_X   = 3,
    ESC_FGCOLOR = 4,
    ESC_BGCOLOR = 5;

public static Color getColor(int i)
{
    return COLORS[i & 0x0f];
}
/*
public static void main(String[] args)
{
    final Frame f = new Frame("Terminal Test");
    f.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                f.dispose();
            }
        } );

    final Terminal term = new Terminal();
    // disabling forward focus traversal allows TAB to reach the component
    term.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,
            Collections.EMPTY_SET);
    term.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                // handle special keys
                switch(ke.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        term.print("\033A");
                        term.repaint();
                        break;
                    case KeyEvent.VK_DOWN:
                        term.print("\033B");
                        term.repaint();
                        break;
                    case KeyEvent.VK_RIGHT:
                        term.print("\033C");
                        term.repaint();
                        break;
                    case KeyEvent.VK_LEFT:
                        term.print("\033D");
                        term.repaint();
                        break;
                }
            }

            public void keyTyped(KeyEvent ke) {
                char c = ke.getKeyChar();
                if( c == KeyEvent.CHAR_UNDEFINED ) {
                    // handle special keys
                    switch(ke.getKeyCode()) {
                        case KeyEvent.VK_TAB:
                            c = '\t';
                            break;
                        case KeyEvent.VK_ESCAPE:
                            c = (char)27;
                            break;
                    }
                }
                if( c != KeyEvent.CHAR_UNDEFINED ) {
                    term.print(c);
                    term.repaint();
                }
            }
        } );

    f.add(term, BorderLayout.CENTER);
    term.validate();
    f.pack();
    f.setVisible(true);

    term.println(RED, WHITE, "READY");
    term.requestFocus();
}
*/

private int tileWidth_ = 8;
private int tileHeight_ = 12;
private TerminalLine[] line_;
private int xCursor_;
private int yCursor_;
private int xCursorSave_;
private int yCursorSave_;
private boolean wrap_ = true;
private boolean scroll_ = true;
private boolean cursorVisible_ = true;
private int tab_ = 8;
private int xOffset_ = 0;
private int yOffset_ = 0;
private int escapeMode_ = ESC_NORMAL;
/** First character held in an ESC Y Position Cursor command. */
private int escapeHold_ = -1;
private boolean reverseVideo_;
private byte effects_;

private Image buffer_;

/** Creates a new terminal.  */
public Terminal()
{
    line_ = new TerminalLine[HEIGHT];
    for(int y = 0; y < HEIGHT; ++y) {
        line_[y] = new TerminalLine(WIDTH);
    }

    setBackground(WHITE);
    setForeground(BLACK);
    setFont( new Font("Monospaced", Font.PLAIN, 12) );
    clearScreen();
}

/** Creates a new terminal, with width w and height h  */
public Terminal(int w, int h)
{
	WIDTH = w;
	HEIGHT = h;
	
    line_ = new TerminalLine[HEIGHT];
    for(int y = 0; y < HEIGHT; ++y) {
        line_[y] = new TerminalLine(WIDTH);
    }

    setBackground(WHITE);
    setForeground(BLACK);
    setFont( new Font("Monospaced", Font.PLAIN, 12) );
    clearScreen();
}

/** Rings the system bell. */
public void bell()
{
    Toolkit.getDefaultToolkit().beep();
}

/** Clears the screen by setting every character to space, with default colors,
 * and repaints.  */
public synchronized void clearScreen()
{
    for(int y = 0; y < HEIGHT; ++y) {
        clearLine(y);
    }
    repaint();
}

/** Clears an entire line. */
public synchronized void clearLine(int line)
{
    clearLine(line, 0, WIDTH-1);
}

/** Clears a line from positions x0 to x1, inclusive. */
public synchronized void clearLine(int y, int x0, int x1)
{
    Color bg = getBackground();
    Color fg = getForeground();
    TerminalLine line = line_[y];
    for(int x = x0; x <= x1; ++x) {
        line.data[x] = ' ';
        line.bgColor[x] = bg;
        line.fgColor[x] = fg;
        line.effects[x] = 0;
    }
}

/** If true, the cursor is shown as an inverse-video block.  Default is true. */
public synchronized boolean getCursorVisible()
{ return cursorVisible_; }

/** The character at a given screen coord. */
public synchronized char getData(int x, int y)
{
    return line_[y].data[x];
}

/** The background color at a given screen coord, or Terminal background if
 * null. */
public synchronized Color getBgColor(int x, int y)
{
    return line_[y].bgColor[x];
}

/** The effects at a given screen coord. */
public synchronized byte getEffects(int x, int y)
{
    return line_[y].effects[x];
}

/** The foreground color at a given screen coord, or Terminal foreground if
 * null. */
public synchronized Color getFgColor(int x, int y)
{
    return line_[y].fgColor[x];
}

public synchronized Dimension getMaximumSize()
{
    return getPreferredSize();
}

public synchronized Dimension getMinimumSize()
{
    return getPreferredSize();
}

public synchronized Dimension getPreferredSize()
{
    return new Dimension(WIDTH * tileWidth_, HEIGHT * tileHeight_);
}

/** If true, all drawing is done in reverse--background color on foreground. */
public boolean getReverseVideo()
{ return reverseVideo_; }

/** If true, the Terminal will scroll up one line when the last character is
  * printed to.  Default is true.
  */
public synchronized boolean getScroll()
{ return scroll_; }

/** The horizontal tab width, default is 8. */
public synchronized int getTab()
{ return tab_; }

/** Width of each character space. */
public int getTileWidth()
{ return tileWidth_; }

/** Height of each character space. */
public int getTileHeight()
{ return tileHeight_; }

/** The horizontal text offset fudge factor, default is 0. */
public synchronized int getXOffset()
{ return xOffset_; }

/** The vertical text offset fudge factor, default is -(descent of current
 * font). */
public synchronized int getYOffset()
{ return yOffset_; }

/** The x-coordinate of the cursor, counting 0 to WIDTH-1. */
public synchronized int getXCursor()
{ return xCursor_; }

/** The y-coordinate of the cursor, counting 0 to HEIGHT-1. */
public synchronized int getYCursor()
{ return yCursor_; }

/** Moves the cursor to x, y. */
public synchronized void gotoxy(int x, int y)
{
    xCursor_ = x;
    yCursor_ = y;
}

/** Prints text at the current cursor position and calls repaint. */
public synchronized void print(String s)
{
    print(null, null, s);
}

/** Prints text at the current cursor position, in the given colors, and calls
 * repaint. */
public synchronized void print(Color bg, Color fg, String s)
{
    for(int i = 0, len = s.length(); i < len; ++i) {
        print(bg, fg, s.charAt(i) );
    }
    repaint();
}

/** Prints a single character at the current cursor position.  Does not call
  * repaint, so to see the results when printing single characters, add a
  * repaint() call at the end.
  */
public synchronized void print(char c)
{
    print(null, null, c);
}

/** Prints a single character at the current cursor position.  Does not call
  * repaint, so to see the results when printing single characters, add a
  * repaint() call at the end.
  */
public synchronized void print(Color bg, Color fg, char c)
{
    if( escapeMode_ == ESC_NORMAL ) {
        switch(c) {
            case 7:     //  7 = BEL, bell
                bell();
                break;
            case '\b':  //  8 = BS, backspace
                --xCursor_;
                break;
            case '\t':  //  9 = HT, tab
                xCursor_ = ((xCursor_ + tab_) / tab_) * tab_;
                break;
            case '\n':  // 10 = LF, newline
                xCursor_ = 0;
                ++yCursor_;
                break;
            case 11:    // 11 = VT, vertical tab
            case '\f':  // 12 = FF, form feed
                ++yCursor_;
                break;
            case '\r':  // 13 = CR, ignored because Java's newline is \n
                break;
            case 27:    // 27 = ESC, enters escape mode
                escapeMode_ = ESC_ESCAPE;
                break;
            case 127: { // 127 = DEL, right-delete
                int yc = yCursor_;
                TerminalLine line = line_[yc];
                for(int x = xCursor_; x < WIDTH-1; ++x) {
                    line.setData(x, line.bgColor[x+1], line.fgColor[x+1],
                        line.data[x+1], line.effects[x+1] );
                }
                setData(WIDTH-1, yc, null, null, ' ');
                break;
            }
            default:
                if( c < 32 ) {
                    // unknown control character
                    bell();
                } else {
                    setData(xCursor_, yCursor_, bg, fg, c);
                    ++xCursor_;
                }
        }
    } else {
        escapeMode_ = doEscape(c);
    }
    // wrap cursor
    if( xCursor_ < 0 ) {
        if( wrap_ ) {
            xCursor_ = WIDTH-1;
            --yCursor_;
        } else {
            xCursor_ = 0;
        }
    } else if( xCursor_ >= WIDTH ) {
        if( wrap_ ) {
            xCursor_ = 0;
            ++yCursor_;
        } else {
            xCursor_ = WIDTH-1;
        }
    }
    if( yCursor_ < 0 ) {
        if( scroll_ ) {
            scrollDown(0);
        }
        yCursor_ = 0;
    } else if( yCursor_ >= HEIGHT ) {
        if( scroll_ ) {
            scrollUp(0);
        }
        yCursor_ = HEIGHT-1;
    }
}

public synchronized void scrollDown(int topline)
{
    // scroll down, exposing a blank line at top
    // don't waste the existing array, recycle it.
    TerminalLine line0 = line_[HEIGHT-1];
    // move all lines down
    for(int y = HEIGHT-1; y > topline; --y) {
        line_[y] = line_[y-1];
    }
    // put it at the top
    line_[topline] = line0;
    clearLine(topline);
}

public synchronized void scrollUp(int topline)
{
    // scroll up, exposing a blank line at bottom
    // don't waste the existing array, recycle it.
    TerminalLine line0 = line_[topline];
    // move all lines up
    for(int y = topline; y < HEIGHT-1; ++y) {
        line_[y] = line_[y+1];
    }
    // put it at the end
    line_[HEIGHT-1] = line0;
    clearLine(HEIGHT-1);
}

private int doEscape(char c)
{
    switch(escapeMode_) {
        case ESC_NORMAL:
            throw new IllegalStateException();
        case ESC_ESCAPE:
            // handled below
            break;
        case ESC_POS_Y:
            escapeHold_ = Math.max(0, Math.min(HEIGHT-1, (int)(c - 32)));
            return ESC_POS_X;
        case ESC_POS_X:
            yCursor_ = escapeHold_;
            xCursor_ = Math.max(0, Math.min(WIDTH-1, (int)(c - 32)));
            return ESC_NORMAL;
        case ESC_FGCOLOR:
            setForeground( getColor( (int)c ) );
            return ESC_NORMAL;
        case ESC_BGCOLOR:
            setBackground( getColor( (int)c ) );
            return ESC_NORMAL;
        default:
            throw new IllegalStateException("Unknown escape mode "+escapeMode_);
    }

    switch(c) {
        case 24:        // 24 = CAN, cancel escape
        case 26:        // 26 = SUB, cancel escape
        case 27:        // 27 = ESC, cancel escape
            return ESC_NORMAL;
        case 'A':       // Cursor Up
            if( yCursor_ > 0 ) {
                --yCursor_;
            }
            return ESC_NORMAL;
        case 'B':       // Cursor Down
            if( yCursor_ < HEIGHT-1 ) {
                ++yCursor_;
            }
            return ESC_NORMAL;
        case 'C':       // Cursor Forward
            if( xCursor_ < WIDTH-1 ) {
                ++xCursor_;
            }
            return ESC_NORMAL;
        case 'D':       // Cursor Backward
            if( xCursor_ > 0 ) {
                --xCursor_;
            }
            return ESC_NORMAL;
        case 'E':       // Clear Screen, Home Cursor
            clearScreen();
            xCursor_ = 0;
            yCursor_ = 0;
            return ESC_NORMAL;
        case 'H':       // Home Cursor
            xCursor_ = 0;
            yCursor_ = 0;
            return ESC_NORMAL;
        case 'I':       // Reverse Index
            --yCursor_;
            return ESC_NORMAL;
        case 'J':       // Erase to End of Page
            clearLine(yCursor_, xCursor_, WIDTH-1);
            for(int y = yCursor_+1; y < HEIGHT; ++y) {
                clearLine(y);
            }
            return ESC_NORMAL;
        case 'K':       // Erase to End of Line
            clearLine(yCursor_, xCursor_, WIDTH-1);
            return ESC_NORMAL;
        case 'L':       // Insert Line
            scrollDown(yCursor_);
            xCursor_ = 0;
            return ESC_NORMAL;
        case 'M':       // Delete Line
            scrollUp(yCursor_);
            xCursor_ = 0;
            return ESC_NORMAL;
        case 'N': {     // Delete Character
            int yc = yCursor_;
            TerminalLine line = line_[yc];
            for(int x = xCursor_; x < WIDTH-1; ++x) {
                line.setData(x, line.bgColor[x+1], line.fgColor[x+1],
                    line.data[x+1], line.effects[x+1] );
            }
            setData(WIDTH-1, yc, null, null, ' ');
            break;
        }
        case 'Y':       // Position Cursor
            return ESC_POS_Y;
        case 'b':       // Set Foreground Color
            return ESC_FGCOLOR;
        case 'c':       // Set Background Color
            return ESC_BGCOLOR;
        case 'd': {     // Erase from Beginning of Display
            int xc = xCursor_, yc = yCursor_;
            clearLine(yc, 0, xc);
            for(int y = 0; y < yc; ++y) {
                clearLine(y);
            }
            return ESC_NORMAL;
        }
        case 'j':       // Save Cursor Position
            xCursorSave_ = xCursor_;
            yCursorSave_ = yCursor_;
            return ESC_NORMAL;
        case 'k':       // Restore Cursor Position
            xCursor_ = xCursorSave_;
            yCursor_ = yCursorSave_;
            return ESC_NORMAL;
        case 'l':       // Erase Entire Line
            clearLine(yCursor_);
            xCursor_ = 0;
            return ESC_NORMAL;
        case 'm':       // Enable Cursor
            cursorVisible_ = true;
            return ESC_NORMAL;
        case 'n':       // Disable Cursor
            cursorVisible_ = false;
            return ESC_NORMAL;
        case 'o':       // Erase from Beginning of Line
            clearLine(yCursor_, 0, xCursor_);
            return ESC_NORMAL;
        case 'p':       // Enable Reverse Video
            reverseVideo_ = true;
            return ESC_NORMAL;
        case 'q':       // Disable Reverse Video
            reverseVideo_ = false;
            return ESC_NORMAL;
        case 'r':       // Enable Underlining
            effects_ |= EFFECTS_UNDERLINE;
            return ESC_NORMAL;
        case 'u':       // Disable Underlining
            effects_ &= ~EFFECTS_UNDERLINE;
            return ESC_NORMAL;
        case 'v':       // Enable Wrapping
            wrap_ = true;
            return ESC_NORMAL;
        case 'w':       // Disable Wrapping
            wrap_ = false;
            return ESC_NORMAL;
    }
    // unknown escape code
    bell();
    return ESC_NORMAL;
}

/** Prints text and a newline at the current cursor position, and calls
 * repaint. */
public synchronized void println(String s)
{
    println(null, null, s);
}

/** Prints text and a newline at the current cursor position, and calls
 * repaint. */
public synchronized void println(Color bg, Color fg, String s)
{
    print(bg, fg, s);
    print(bg, fg, '\n');
    repaint();
}

/** If true, the cursor is shown as an inverse-video block.  Default is true. */
public synchronized void setCursorVisible(boolean c)
{ cursorVisible_ = c; }

/** The character at a given screen coord, default colors and effects. */
public synchronized void setData(int x, int y, char c)
{
    setData(x, y, null, null, c);
}

/** The character at a given screen coord and colors, default effects. */
public synchronized void setData(int x, int y, Color bg, Color fg, char c)
{
    setData(x, y, bg, fg, c, effects_);
}

/** The character at a given screen coord, colors, and effects. */
public synchronized void setData(int x, int y, Color bg, Color fg, char c, byte effects)
{
    if( bg == null ) {
        bg = getBackground();
    }
    if( fg == null ) {
        fg = getForeground();
    }
    if( reverseVideo_ ) {
        Color tmp = bg;
        bg = fg;
        fg = tmp;
    }
    TerminalLine line = line_[y];
    line.data[x] = c;
    line.bgColor[x] = bg;
    line.fgColor[x] = fg;
    line.effects[x] = effects;
}

/** The background color at a given screen coord, or Terminal background if
 * null. */
public synchronized void setBgColor(int x, int y, Color b)
{
    line_[y].bgColor[x] = b;
}

/** The effects at a given screen coord. */
public synchronized void setEffects(int x, int y, byte e)
{
    line_[y].effects[x] = e;
}

/** The foreground color at a given screen coord, or Terminal foreground if
 * null. */
public synchronized void setFgColor(int x, int y, Color f)
{
    line_[y].fgColor[x] = f;
}

public void setFont(Font font)
{
    super.setFont(font);
    FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(font);
    tileWidth_ = fm.charWidth('W');
    tileHeight_ = fm.getHeight() - fm.getDescent();
    xOffset_ = 0;
    yOffset_ = -fm.getDescent();
    //System.out.println("tile "+tileWidth_+"x"+tileHeight_+", offset="+xOffset_+","+yOffset_);
}

/** If true, all drawing is done in reverse--background color on foreground. */
public void setReverseVideo(boolean r)
{ reverseVideo_ = r; }

/** If true, the Terminal will scroll up one line when the last character is
  * printed to.  Default is true.
  */
public synchronized void setScroll(boolean s)
{ scroll_ = s; }

/** The horizontal tab width, default is 8. */
public synchronized void setTab(int t)
{ tab_ = t; }

/** Width and height of each character space. */
public void setTileSize(int w, int h)
{
    tileWidth_ = w;
    tileHeight_ = h;
    buffer_ = null;
}

/** The horizontal text offset fudge factor, default is 0.  Adjust depending
  * on the tile size chosen.
  */
public synchronized void setXOffset(int x)
{ xOffset_ = x; }

/** The vertical text offset fudge factor, default is -(descent of current
 * font).  Adjust depending on the tile size chosen.
  */
public synchronized void setYOffset(int y)
{ yOffset_ = y; }

/** The x-coordinate of the cursor, counting 0 to WIDTH-1. */
public synchronized void setXCursor(int x)
{ xCursor_ = x; }

/** The y-coordinate of the cursor, counting 0 to HEIGHT-1. */
public synchronized void setYCursor(int y)
{ yCursor_ = y; }

public synchronized void paint(Graphics g)
{
    if( buffer_ != null ) {
        g.drawImage(buffer_, 0, 0, null);
    }
}

public synchronized void update(Graphics gg)
{
    if( buffer_ == null ) {
        Dimension d = getPreferredSize();
        buffer_ = createImage(d.width, d.height);
        if( buffer_ == null ) {
            repaint(100L);
            return;
        }
    }

    Graphics g = buffer_.getGraphics();
    g.setFont( getFont() );
    FontMetrics fm = g.getFontMetrics();
    char[] cbuf = new char[1];  // for drawChars
    for(int y = 0; y < HEIGHT; ++y) {
        int sy = y * tileHeight_;
        TerminalLine line = line_[y];
        for(int x = 0; x < WIDTH; ++x) {
            int sx = x * tileWidth_;
            // background
            g.setColor( line.bgColor[x] );
            g.fillRect(sx, sy, tileWidth_, tileHeight_);
            // character
            g.setColor( line.fgColor[x] );
            cbuf[0] = line.data[x];
            g.drawChars(cbuf, 0, 1, sx + xOffset_,
                    sy + fm.getAscent() + yOffset_);
            // effects
            if( (line.effects[x] & EFFECTS_UNDERLINE) != 0 ) {
                g.drawLine(sx, sy+tileHeight_-1,
                        sx+tileWidth_-1, sy+tileHeight_-1);
            }
            // borders for testing
            //g.drawLine(sx, sy, sx+tileWidth_-1, sy);
            //g.drawLine(sx, sy, sx, sy+tileHeight_-1);
        }
    }
    if( cursorVisible_ ) {
        Color bg = getBgColor(xCursor_, yCursor_);
        if( bg == BLACK ) {
            bg = WHITE;
        }
        g.setXORMode(bg);
        g.fillRect(xCursor_ * tileWidth_, yCursor_ * tileHeight_,
                tileWidth_, tileHeight_);
        g.setPaintMode();
    }

    paint(gg);
}

} // Terminal

//________________________________________
class TerminalLine
{
public Color[] bgColor;
public Color[] fgColor;
public char[] data;
public byte[] effects;

/** Creates a new Terminal line of a given width. */
public TerminalLine(int width)
{
    bgColor = new Color[width];
    fgColor = new Color[width];
    data = new char[width];
    effects = new byte[width];
}

public void setData(int x, Color bg, Color fg, char c, byte e)
{
    data[x] = c;
    bgColor[x] = bg;
    fgColor[x] = fg;
    effects[x] = e;
}

} // TerminalLine