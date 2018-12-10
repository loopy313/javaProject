package common;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TypingTest1 {
	public static void main(String[] args) {
		MFrame t = new MFrame();
	}
}

class MFrame extends JFrame implements KeyListener {

	JTextField inputText;
	JTextArea outcome;
	JLabel staticText;
	ArrayList<String> a;
	private long start;
	private long end;
	String[] word = { "Java", "Iterator", "com", "hasing", "JVM", "primitive type", "Scanner",
						"constant", "literal", "casting", "switch", "continue", "recursive call"
					};

	public MFrame() {
		this.setSize(300, 200);
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("대덕 도서관 타자 연습");

		JPanel panel = new JPanel();

		inputText = new JTextField(10);
		outcome = new JTextArea("결과창");
		staticText = new JLabel("Java World");
		JLabel resultText = new JLabel("Java World");

		panel.add(inputText);

		add(staticText, BorderLayout.NORTH);
		add(outcome, BorderLayout.SOUTH);
		add(panel);

		inputText.addKeyListener(this);

		start = System.currentTimeMillis();
		String[] word = { "Java", "Iterator", "com", "hasing", "JVM", "primitive type", "Scanner",
				"constant", "literal", "casting", "switch", "continue", "recursive call" 
				};
		a = new ArrayList<String>();

		for (int i = 0; i < word.length; i++)
			a.add(word[i]);

		setVisible(true);

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (staticText.getText().equals(inputText.getText())) {// 스트링만 예외로
				Random r = new Random();
				end = System.currentTimeMillis();
				
				outcome.setText("맞았습니다" + "   " + inputText.getText().length()
						/ ((end - start) / 1000.0/60.0 ) + "타수");

				staticText.setText(a.get(r.nextInt(a.size())));
				inputText.setText("");
			} else {
				outcome.setText("틀렸습니다");
				inputText.setText("");
			}
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
}
