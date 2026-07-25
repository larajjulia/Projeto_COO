import java.util.ArrayList;

/***********************************************************************/
/*                                                                     */
/* Para jogar:                                                         */
/*                                                                     */
/*    - cima, baixo, esquerda, direita: movimentação do player.        */
/*    - control: disparo de projéteis.                                 */
/*    - ESC: para sair do jogo.                                        */
/*                                                                     */
/***********************************************************************/

//Função para controlar o comportamento do boss


public class Main {
    public static final int ACTIVE = Game_Object.ACTIVE;
    public static final int INACTIVE = Game_Object.INACTIVE;
    public static final int EXPLODING = Game_Object.EXPLODING;
	public static long initialTime; //variavel de controle de tempo
	public static Boss bossAtivo = null; // indica a presença ou não de boss
	public static int typeBoss = 1; // determina o tipo do boss a ser instanciado
	public static long phaseDelay; // delay entre fases do jogo
	/* Espera, sem fazer nada, até que o instante de tempo atual seja */
	/* maior ou igual ao instante especificado no parâmetro "time.    */
	
	public static void busyWait(long time){
		
		while(System.currentTimeMillis() < time) Thread.yield();
	}

	/* Método principal */
	
	public static void main(String [] args){

		/* Indica que o jogo está em execução */

		boolean running = true;

		/* variáveis do player */

		Initial_Data readFirstFile = ReadFiles.Read_Initial_File();
		Player player1 = new Player(readFirstFile.getPlayer_Life());
		int phaseNow = 0;
		Phase_Info phaseInfo = Phase_Info.Read_Enemy_Info(readFirstFile.Phases().get(phaseNow));						

		/* variáveis dos projéteis disparados pelo player */
		Projectile.projectilePlayer(GameLib.WIDTH / 2, GameLib.HEIGHT * 0.90 - player1.getRadius(), 1.25, 1.25); 	
		/* variáveis dos projéteis lançados pelos inimigos (tanto tipo 1, quanto tipo 2) */
		Projectile.projectileEnemy(0.0, 0.0, 0.0, 0.0); 
		
		/* estrelas que formam o fundo de primeiro plano */
		Background bg1 = Background.Background_1();
		Background bg2 = Background.Background_2();
						
		/* iniciado interface gráfica */
		
		GameLib.initGraphics();
		initialTime = System.currentTimeMillis();
		//GameLib.initGraphics_SAFE_MODE();  // chame esta versão do método caso nada seja desenhado na janela do jogo.
		
		/*************************************************************************************************/
		/*                                                                                               */
		/* Main loop do jogo                                                                             */
		/* -----------------                                                                             */
		/*                                                                                               */
		/* O main loop do jogo executa as seguintes operações:                                           */
		/*                                                                                               */
		/* 1) Verifica se há colisões e atualiza estados dos elementos conforme a necessidade.           */
		/*                                                                                               */
		/* 2) Atualiza estados dos elementos baseados no tempo que correu entre a última atualização     */
		/*    e o timestamp atual: posição e orientação, execução de disparos de projéteis, etc.         */
		/*                                                                                               */
		/* 3) Processa entrada do usuário (teclado) e atualiza estados do player conforme a necessidade. */
		/*                                                                                               */
		/* 4) Desenha a cena, a partir dos estados dos elementos.                                        */
		/*                                                                                               */
		/* 5) Espera um período de tempo (de modo que delta seja aproximadamente sempre constante).      */
		/*                                                                                               */
		/*************************************************************************************************/
		
		while(running){

			// public static long currentTime = System.currentTimeMillis();
    		// public static long delta = System.currentTimeMillis() - currentTime;

			Game_Object.updateTime();

			/***************************/
			/* Verificação de colisões */
			/***************************/
						
			if(player1.getState() == ACTIVE){
				
				/* colisões player - projeteis (inimigo) */
				
				for (Projectile item : Projectile.listProjectiles)
				 player1.collisionPlayer(item);
			
				/* colisões player - inimigos */
							
				for (Enemy item : Enemy.listEnemies){
					player1.collisionPlayer(item);
				}
				 
				
			}
			
			/* colisões projeteis (player) - inimigos */
			for (Enemy itemEnemy : Enemy.listEnemies){
				if(itemEnemy instanceof Powerup) continue;
				for (Projectile item : Projectile.listProjectiles){
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

			
			
			/* Verificando se a explosão do player já acabou.         */
			/* Ao final da explosão, o player volta a ser controlável */
			player1.hasExploded();
			
			for(Powerup powerup : new ArrayList<>(Powerup.listPowerup)){
				powerup.powerUpEnd(player1);
			}
			
			/********************************************/
			/* Verificando entrada do usuário (teclado) */
			/********************************************/
			
			if(player1.getState() == ACTIVE){
				player1.movement();
				
				if(GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
					player1.readyToShoot(); /* checando se o player pode atirar */
				}
			}
			
			if(GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) running = false;
			
			/* Verificando se coordenadas do player ainda estão dentro */
			/* da tela de jogo após processar entrada do usuário.      */
			
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
			
			
			//começo do setup dos inimigos

			for(Enemy_Info info : phaseInfo.enemies){
                if(!info.spawned && Game_Object.currentTime - initialTime >= info.when){
					if(info.powerup == false){
						if(info.type == 1) new Enemy_Type1(info.X, info.Y, 0.0, 0.0, 0.5);
						else new Enemy_Type2(info.X, info.Y, 0.0, 0.0, 0.5);
	
					}
					else{
						if(info.type == 1) new Powerup_1(info.X, info.Y, 0.3, 0.0, 0.5);
						else new Powerup_2(info.X, info.Y, 0.15, 0.0, 0.5);
					}
					info.spawned = true;
				}
            }

			//spawn dos bosses
			if(phaseInfo.boss != null && phaseInfo.boss.spawned == false && bossAtivo == null && Game_Object.currentTime - initialTime >= phaseInfo.boss.when){
				bossAtivo = Boss.bossApplication(phaseInfo.boss);
				phaseInfo.boss.spawned = true;
			}

			//verificação de atualização de estados
			if(bossAtivo != null){
				if(bossAtivo.getState() == INACTIVE){
					bossAtivo = null;
				}
				else bossAtivo.adjustMovement();
			}

			//checagem se acabou o spawn de inimigos
			boolean endEnemies = true;
			for(Enemy_Info enemy : phaseInfo.enemies){
				if(enemy.spawned == false){
					endEnemies = false;
					break;
				}
			}

			//checagem se acabou o boss e deve acabar a fase
			boolean endBoss = false;
			if(phaseInfo.boss == null || phaseInfo.boss.spawned == true && bossAtivo == null){
				endBoss = true;
			}

			//define que a fase acabou, seta um delay entre as fases
			if(endBoss && endEnemies && Enemy.listEnemies.isEmpty()){
				phaseDelay = Game_Object.currentTime + 3000;
			}

			//comportamento após a fase terminar de fato
			if(phaseDelay != 0 && Game_Object.currentTime >= phaseDelay){
				phaseNow++;
				if(phaseNow > readFirstFile.getPhasesNumber()){
					//aqui printa a mensagem de vitória
					running = false;
				}
				else{
					phaseInfo = Phase_Info.Read_Enemy_Info(readFirstFile.Phases().get(phaseNow));
					initialTime = Game_Object.currentTime;
				}
			}
			
	
			/* chamada a display() da classe GameLib atualiza o desenho exibido pela interface do jogo. */
			
			GameLib.display();
			
			/* faz uma pausa de modo que cada execução do laço do main loop demore aproximadamente 3 ms. */
			
			busyWait(Game_Object.currentTime + 3);
		}
		
		System.exit(0);
	}

}