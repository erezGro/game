package buttons;

import game.Game;
import game.AudioPlayer.MusicStatus;

public class MusicButton extends GameButton{
	
	public MusicButton(String id, String name, int posX, int posY) {
		super(id, name, 100, 40, posX, posY);
	}

	@Override
	public void buttonAction() {
		super.buttonAction();
		if (Game.audioPlayer().getStatus() == MusicStatus.STOPPED) {
			Game.audioPlayer().play(game.Game.gameMusicPath, 0);
			this.setText("Mute");
		}
		else {
			Game.audioPlayer().stop();
			this.setText("Music");
		}
	}

}
