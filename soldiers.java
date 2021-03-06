package candm;

import java.util.ArrayList;
import java.util.List;
enum Position {R, L}
public class soldiers {

	public static void main(String[] args) {
		State initial = new State (3,3,3, Position.L, 0,0,0);
		IDFS find = new IDFS();
		State final_solution = find.call(initial);
		print(final_solution);

	}
	
	private static void print(State final_solution) {
		if (final_solution == null) {
			System.out.print("No solution");
		} else {
			List<State> final_path = new ArrayList<State>();
			State check = final_solution;
			while(check != null) {
				final_path.add(check);
				check = check.getParent();
			}
			
			int depth = final_path.size() - 1;
			for (int i = depth; i >= 0; i--) {
			 check = final_path.get(i);
				if (check.checkGoal()) {
					System.out.print(check.toString());
				} else {
					System.out.print(check.toString());
					System.out.print("\n");
				}
			}
		}

}
}
class State {

	public int RedLeft,BlueLeft,GreenLeft;
	public int RedRight,BlueRight,GreenRight;
	public Position boat;
	private State parentState;

	public State(int BlueLeft, int GreenLeft,int RedLeft, Position boat,
			int BlueRight, int GreenRight,int RedRight) {
		this.RedLeft = RedLeft;
		this.BlueLeft = BlueLeft;
		this.GreenLeft = GreenLeft;
		this.boat = boat;
		this.RedRight = RedRight;
		this.BlueRight = BlueRight;
		this.GreenRight = GreenRight;
	}

	public boolean checkGoal() {
		
		return BlueLeft == 0 && RedLeft == 0 && GreenLeft== 0;
	}

	public boolean checkValid() {
		
		if (RedLeft >= 0 && RedRight >= 0 && BlueLeft >= 0 && BlueRight >= 0 && GreenLeft >= 0 && GreenRight >= 0 )
			return true;
		
		return false;
	}

	public List<State> generatechildren() {
		List<State> children = new ArrayList<State>();
		if (boat == Position.L) {
			validate(children, new State(BlueLeft-1, GreenLeft-1,RedLeft, Position.R,BlueRight+1,GreenRight+1,RedRight));
			validate(children, new State(BlueLeft-1, GreenLeft,RedLeft-1, Position.R,BlueRight+1,GreenRight,RedRight+1));
			validate(children, new State(BlueLeft, GreenLeft-1,RedLeft-1, Position.R,BlueRight,GreenRight+1,RedRight+1));
			
		} else {
			validate(children, new State(BlueLeft+1, GreenLeft,RedLeft, Position.L,BlueRight-1,GreenRight,RedRight));
			validate(children, new State(BlueLeft, GreenLeft+1,RedLeft, Position.L,BlueRight,GreenRight-1,RedRight));
			validate(children, new State(BlueLeft, GreenLeft,RedLeft+1, Position.L,BlueRight,GreenRight,RedRight-1));
		}
		return children;
	}

	private void validate(List<State> children, State next) {
		if (next.checkValid()) {
			next.setParent(this);
			children.add(next);
		}
	}

	public void setParent(State parentState) {
		this.parentState = parentState;
	}
	
	public State getParent() {
		return parentState;
	}

	

	@Override
	public String toString() {
		if (boat == Position.R) 
			return "(" + BlueLeft + "," + GreenLeft +","+RedLeft +",R,"+ BlueRight + "," + GreenRight +","+RedRight+ ")";
	 else 
			return "(" + BlueLeft + "," + GreenLeft +","+RedLeft +",L,"+ BlueRight + "," + GreenRight +","+RedRight+ ")";
     }	
}

class IDFS {
	public State call(State initial) {
		State result=null;
		for(int i=0;i<20;i++) {
	       result =IDFSloop(initial,i);
	       if(result != null) {
	    	  return  result; 
	       }
		}
		return null;
	}

	private State IDFSloop(State state, int limit) {
		if (state.checkGoal()) {
			return state;
		} else if (limit <= 0) {
			return null;
		} else {
			List<State> kids = state.generatechildren();
			for (State child : kids) {
				State result = IDFSloop(child, limit - 1);
				if (result !=null)
					return result;
			}
			return null;
		}
	}
}

