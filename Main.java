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

public class Main {
    public static final int ACTIVE = Game_Object.ACTIVE;
    public static final int INACTIVE = Game_Object.INACTIVE;
    public static final int EXPLODING = Game_Object.EXPLODING;
	public static final long initialTime = System.currentTimeMillis(); //tempo de inicio do jogo
	public static Boss_1 bossAtivo = null;
	/* Espera, sem fazer nada, até que o instante de tempo atual seja */
	/* maior ou igual ao instante especificado no parâmetro "time.    */
	
	public static void busyWait(long time){
		
		while(System.currentTimeMillis() < time) Thread.yield();
	}

	/* Método principal */
	
	public static void main(String [] args){

		/* Indica que o jogo está em execução */

		boolean running = true;
		boolean boss = false;

		/* variáveis do player */
		
		Player player1 = new Player();						// estado

		/* variáveis dos projéteis disparados pelo player */
		
		Projectile.projectilePlayer(GameLib.WIDTH / 2, GameLib.HEIGHT * 0.90 - player1.getRadius(), 1.25, 1.25); 	

		/* variáveis dos inimigos/powerup tipo 1 */
		new Enemy_Type1(GameLib.WIDTH / 8, -10.0, 0.0, 0.0, 0.5);
		new Powerup_1(GameLib.WIDTH / 3, -9.0, 0.0, 0.0, 0.5);

		/* variáveis dos inimigos/powerup tipo 2 */
		new Enemy_Type2(GameLib.WIDTH / 2, -10.0, 0.0, 0.0, 0.5);
		new Powerup_2(GameLib.WIDTH / 4, -9.0, 0.0, 0.0, 0.5);
		
		/* variáveis dos projéteis lançados pelos inimigos (tanto tipo 1, quanto tipo 2) */
		Projectile.projectileEnemy(0.0, 0.0, 0.0, 0.0); 
		
		/* estrelas que formam o fundo de primeiro plano */
		Background bg1 = Background.Background_1();
		Background bg2 = Background.Background_2();
						
		/* iniciado interface gráfica */
		
		GameLib.initGraphics();
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

			/* verificando se novos inimigos devem ser "lançados" */
			
			for (Enemy element : new ArrayList<>(Enemy.listEnemies)){
				element.addEnemy();
			}
			
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
			
			

			//começo do setup dos bosses
			if(Game_Object.currentTime - initialTime >= 3000 && boss == false){
				for (Enemy item : Enemy.listEnemies){
					if(item instanceof Powerup) continue;
					item.nextEnemy((long)10000000);
				}
				bossAtivo = new Boss_1(GameLib.WIDTH/2, -10.0, 0.40, Math.PI/2, 0.0);
				bossAtivo.visualEnemies();
				boss = true;
	
			}

			if(boss == true){
				if(bossAtivo.hasExploded()) boss = false;
				else bossAtivo.adjustMovement();	
			}
			/* chamada a display() da classe GameLib atualiza o desenho exibido pela interface do jogo. */
			
			GameLib.display();
			
			/* faz uma pausa de modo que cada execução do laço do main loop demore aproximadamente 3 ms. */
			
			busyWait(Game_Object.currentTime + 3);
		}
		
		System.exit(0);
	}
}