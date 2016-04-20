package chromeDino;

import java.util.Arrays;

public class JumpQueue {
	private static final int N = 10;
	private static final long jumpDuration = 470;

	private int[] pre = new int[N];
	private int[] pos = new int[N];
	private int[] peek = new int[N];

	private int prePos = 0;
	private int posPos = 0;
	private int jumPos = -1;

	private long lastJumpTime = 0;

	JumpQueue() {
		for (int i = 0; i < N; i++)
			peek[i] = -1;
	}

	void pre(int t) {
		// System.out.println("PRE: " + t);
		pre[prePos] = t;
		prePos = incr(prePos);
	}

	void pos(int t) {
		// System.out.println("POS: " + t);
		pos[posPos] = t;
		peek[posPos] = calc(posPos);
		posPos = incr(posPos);
		if (jumPos == -1)
			jumPos = incr(jumPos);
	}

	int peek() {
		// System.out.println(Arrays.toString(pre) + "-" + Arrays.toString(pos)
		// + "-" + Arrays.toString(peek));
		if (jumPos == -1)
			return -1;
		if (System.currentTimeMillis() < lastJumpTime + jumpDuration)
			return -1;
		return peek[jumPos];
	}

	void jump() {
		peek[jumPos] = -1;
		jumPos = incr(jumPos);
		lastJumpTime = System.currentTimeMillis();
	}

	int calc(int ti) {
		int pr = pre[ti];
		int po = pos[ti];
		return po - pr + po + offset(pr, po);
	}

	static int incr(int x) {
		return (x + 1) % N;
	}

	@Override
	public String toString() {
		return String.format("%s\n%s\n%s", Arrays.toString(pre),
				Arrays.toString(pos), Arrays.toString(peek));
	}

	static int offset(int pr, int po) {
		// System.out.println("popr: " + (po - pr));
		int d = -1;
		// if (po - pr > 49)
		// d = (int) -((po - pr) * 0.2);
		// else if (po - pr > 44)
		// d = (int) -((po - pr) * 0.30);
		// else if (po - pr > 40)
		// d = (int) -((po - pr) * 0.40);
		// else if (po - pr > 35)
		// d = (int) -((po - pr) * 0.50);
		// else if (po - pr > 30)
		// d = (int) -((po - pr) * 0.60);
		double ratio = -(0.2 + ((50 - (po - pr)) * 2) / 100.0);
		int cons = 0;
		double sub = 0;
		if (ratio < -0.50)
			sub -= 0.05;
		if (ratio < -0.55)
			sub -= 0.05;
		// if (ratio < -0.60)
		// sub -= 0.1;
		if (ratio < -0.65)
			sub -= 0.08;
		ratio += sub;
		if (ratio < -0.9)
			ratio = -0.9;
		d = (int) ((po - pr) * ratio) - cons;
		System.out.println("RATIO: " + ratio);
		return d;
	}
}
