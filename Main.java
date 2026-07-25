import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/***********************************************************************/
/*                                                                     */
/* Para jogar:                                                         */
/*                                                                     */
/*    - cima, baixo, esquerda, direita: movimentação do player.        */
/*    - control: disparo de projéteis.                                 */
/*    - ESC: para sair do jogo.                                        */
/*                                                                     */
/***********************************************************************/

// Função para controlar o comportamento do boss

public class Main {
	public static final int ACTIVE = Game_Object.ACTIVE;
	public static final int INACTIVE = Game_Object.INACTIVE;
	public static final int EXPLODING = Game_Object.EXPLODING;
	public static Boss bossAtivo = null; // indica a presença ou não de boss
	/* Espera, sem fazer nada, até que o instante de tempo atual seja */
	/* maior ou igual ao instante especificado no parâmetro "time. */

	public static void busyWait(long time) {

		while (System.currentTimeMillis() < time)
			Thread.yield();
	}

	private static void spawnEnemy(Enemy_Config config) {
		double angle = (3 * Math.PI) / 2;

		switch (config.Type()) {
			case 1:
				double velocity1 = 0.20 + Math.random() * 0.15;
				new Enemy_Type1(config.positionX(), config.positionY(), velocity1, angle, 0.0);
				break;
			case 2:
				double velocity2 = 0.42;
				new Enemy_Type2(config.positionX(), config.positionY(), velocity2, angle, 0.0);
				break;
		}
	}

	private static void spawnPowerup(PowerUp_Config config) {
		double velocity = 0.20 + Math.random() * 0.15;
		double angle = (3 * Math.PI) / 2;

		switch (config.Type()) {
			case 1:
				new Powerup_1(config.positionX(), config.positionY(), velocity, angle, 0.0);
				break;
			case 2:
				new Powerup_2(config.positionX(), config.positionY(), velocity, angle, 0.0);
				break;
		}
	}

	private static Boss spawnBoss(Boss_Config config) {
		Boss_Info bossInfo = new Boss_Info();
		bossInfo.type = config.Type();
		bossInfo.X = config.positionX();
		bossInfo.Y = config.positionY();
		bossInfo.when = config.Time();
		bossInfo.life = config.Life(); // Boss.java ainda ignora o parâmetro "life" do construtor e fixa 120.0
										// internamente, veja observação abaixo

		return Boss.bossApplication(bossInfo);
	}

	/* Método principal */

	public static void main(String[] args) {

		/* Indica que o jogo está em execução */
		int phaseIndex = 0;
		boolean running = true;
		Initial_Data readFirstFile = ReadFiles.Read_Initial_File();

		/* Inicializa o player */
		Player player1 = new Player(readFirstFile.getPlayer_Life());

		/* Inicializa projéteis disparados pelo player */

		Projectile.projectilePlayer(GameLib.WIDTH / 2, GameLib.HEIGHT * 0.90 - player1.getRadius(), 1.25, 1.25);

		/* variáveis dos inimigos/powerup tipo 1 */
		// new Enemy_Type1(GameLib.WIDTH / 8, -10.0, 0.0, 0.0, 0.5);
		// new Powerup_1(GameLib.WIDTH / 3, -9.0, 0.0, 0.0, 0.5);

		// /* variáveis dos inimigos/powerup tipo 2 */
		// new Enemy_Type2(GameLib.WIDTH / 2, -10.0, 0.0, 0.0, 0.5);
		// new Powerup_2(GameLib.WIDTH / 4, -9.0, 0.0, 0.0, 0.5);

		/*
		 * variáveis dos projéteis lançados pelos inimigos (tanto tipo 1, quanto tipo 2)
		 */
		Projectile.projectileEnemy(0.0, 0.0, 0.0, 0.0);

		/* estrelas que formam o fundo de primeiro plano */
		Background bg1 = Background.Background_1();
		Background bg2 = Background.Background_2();

		/* iniciado interface gráfica */

		GameLib.initGraphics();
		// GameLib.initGraphics_SAFE_MODE(); // chame esta versão do método caso nada
		// seja desenhado na janela do jogo.

		/*************************************************************************************************/
		/*                                                                                               */
		/* Main loop do jogo */
		/* ----------------- */
		/*                                                                                               */
		/* O main loop do jogo executa as seguintes operações: */
		/*                                                                                               */
		/*
		 * 1) Verifica se há colisões e atualiza estados dos elementos conforme a
		 * necessidade.
		 */
		/*                                                                                               */
		/*
		 * 2) Atualiza estados dos elementos baseados no tempo que correu entre a última
		 * atualização
		 */
		/*
		 * e o timestamp atual: posição e orientação, execução de disparos de projéteis,
		 * etc.
		 */
		/*                                                                                               */
		/*
		 * 3) Processa entrada do usuário (teclado) e atualiza estados do player
		 * conforme a necessidade.
		 */
		/*                                                                                               */
		/* 4) Desenha a cena, a partir dos estados dos elementos. */
		/*                                                                                               */
		/*
		 * 5) Espera um período de tempo (de modo que delta seja aproximadamente sempre
		 * constante).
		 */
		/*                                                                                               */
		/*************************************************************************************************/
		while (running && phaseIndex < readFirstFile.getPhasesNumber()) {
			String phaseFile = readFirstFile.Phases().get(phaseIndex);
			Phase_Config phase = Read_Config_Phase.Phase_Reader(phaseFile);

			List<Enemy_Config> Enemies = new ArrayList<>(phase.getEnemy_Configs());
			List<PowerUp_Config> Powerups = new ArrayList<>(phase.getPowerUp_Configs());
			Boss_Config bossConfig = phase.getBoss_Configs();
			boolean bossSpawned = false;

			boolean phaseRunning = true;
			long phaseStart = System.currentTimeMillis();

			while (running && phaseRunning) { // quando passar do segundo boss, o jogo termina

				// public static long currentTime = System.currentTimeMillis();
				// public static long delta = System.currentTimeMillis() - currentTime;

				Game_Object.updateTime();
				long phaseDelta = Game_Object.currentTime - phaseStart;

				Iterator<Enemy_Config> it = Enemies.iterator();
				while (it.hasNext()) {
					Enemy_Config cfg = it.next();
					if (phaseDelta >= cfg.Time()) {
						spawnEnemy(cfg);
						it.remove();
					}
				}

				Iterator<PowerUp_Config> it2 = Powerups.iterator();
				while (it2.hasNext()) {
					PowerUp_Config cfg = it2.next();
					if (phaseDelta >= cfg.Time()) {
						spawnPowerup(cfg);
						it2.remove();
					}
				}

				if (!bossSpawned && bossConfig != null && phaseDelta >= bossConfig.Time()) {
					bossAtivo = spawnBoss(bossConfig);
					bossSpawned = true;
				}

				/***************************/
				/* Verificação de colisões */
				/***************************/

				if (player1.getState() == ACTIVE) {

					/* colisões player - projeteis (inimigo) */

					for (Projectile item : Projectile.listProjectiles)
						player1.collisionPlayer(item);

					/* colisões player - inimigos */

					for (Enemy item : Enemy.listEnemies) {
						player1.collisionPlayer(item);
					}

				}

				/* colisões projeteis (player) - inimigos */
				for (Enemy itemEnemy : Enemy.listEnemies) {
					if (itemEnemy instanceof Powerup)
						continue;
					for (Projectile item : Projectile.listProjectiles) {
						if (item instanceof Projectile_Player)
							itemEnemy.collisionEnemy(item);
					}
				}

				/***************************/
				/* Atualizações de estados */
				/***************************/

				/* projeteis (player e inimigos) */

				for (Projectile item : new ArrayList<>(Projectile.listProjectiles))
					item.updateState();

				/* projeteis (inimigos) */

				/* inimigos tipo 1 */

				for (Enemy_Shooter item : new ArrayList<>(Enemy_Shooter.listEnemyShooters))
					item.readyToShoot(player1);

				// /* verificando se novos inimigos devem ser "lançados" */

				// for (Enemy element : new ArrayList<>(Enemy.listEnemies)) {
				// element.addEnemy();
				// }

				/* Verificando se a explosão do player já acabou. */
				/* Ao final da explosão, o player volta a ser controlável */
				player1.hasExploded();

				if (player1.getState() == INACTIVE && player1.isGameOver()) {
					phaseRunning = false;
					running = false;
				}

				for (Powerup powerup : new ArrayList<>(Powerup.listPowerup)) {
					powerup.powerUpEnd(player1);
				}

				/********************************************/
				/* Verificando entrada do usuário (teclado) */
				/********************************************/

				if (player1.getState() == ACTIVE) {
					player1.movement();

					if (GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
						player1.readyToShoot(); /* checando se o player pode atirar */
					}
				}

				if (GameLib.iskeyPressed(GameLib.KEY_ESCAPE))
					running = false;

				/* Verificando se coordenadas do player ainda estão dentro */
				/* da tela de jogo após processar entrada do usuário. */

				player1.updateX();
				player1.updateY();

				/*******************/
				/* Desenho da cena */
				/*******************/

				/* desenhando plano fundo distante */

				bg1.updateDelta();
				bg1.visualBackground();

				bg2.updateDelta();
				bg2.visualBackground();

				/* desenhando player */

				player1.visualPlayer();

				/* deenhando projeteis (player) */
				/* desenhando projeteis (inimigos) */

				for (Projectile item : Projectile.listProjectiles)
					item.visualProjectile();

				/* desenhando inimigos (tipo 1) */
				/* desenhando inimigos (tipo 2) */

				for (Enemy item : new ArrayList<>(Enemy.listEnemies))
					item.visualEnemies();

				// começo do setup dos bosses

				if (bossAtivo != null) {
					if (bossAtivo.getState() == INACTIVE)
						bossAtivo = null;
					else
						bossAtivo.adjustMovement();
				}

				if (bossSpawned && bossAtivo == null)
					phaseRunning = false;

				GameLib.display();
				busyWait(Game_Object.currentTime + 3);
			}

			phaseIndex++;

		}

		if (player1.isGameOver()) {
			System.out.println("GAME OVER");
			busyWait(System.currentTimeMillis() + 3);
		}

		System.exit(0);
	}

}