package buttons;



import game.Game;


public class ChangeButton extends GameButton{
	
	public ChangeButton(String id, String name, int posX, int posY) {
		super(id, name, 150, 40, posX, posY);		

	}

	private int bkgIndex = 0;
	private String[] bkgArray = {"resources/deepspace1.jpg","resources/Carina_Nebula.jpg"};

	@Override
	public void buttonAction() {
		super.buttonAction();
		if (bkgIndex==0){bkgIndex++;}
		else{bkgIndex=0;}
		Game.UI().canvas().setBackgroundImage(bkgArray[bkgIndex]);
	}

}
