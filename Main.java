import java.awt.Color;

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
		
		Player player1 = new Player();						// estado

		/* variáveis dos projéteis disparados pelo player */
		
		Projectile projectile_player = Projectile.projectilePlayer(0.0, 0.0, 0.0, 0.0); 					
		/* variáveis dos inimigos tipo 1 */
		
		Enemy enemy1 = new Enemy_Type1(0.0, 0.0, 0.0, 0.0, 0.0);

		/* variáveis dos inimigos tipo 2 */
		Enemy enemy2 = new Enemy_Type2(0.0, 0.0, 0.0, 0.0, 0.0);
		
		/* variáveis dos projéteis lançados pelos inimigos (tanto tipo 1, quanto tipo 2) */
		Projectile projectile_enemy1 = Projectile.projectileEnemy(0.0, 0.0, 0.0, 0.0); 
		
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
			
			/***************************/
			/* Verificação de colisões */
			/***************************/
						
			if(player1.getState() == ACTIVE){
				
				/* colisões player - projeteis (inimigo) */
				
				for (Projectile item : Projectile.listProjectiles)
				 player1.collisionPlayer(item);
			
				/* colisões player - inimigos */
							
				for (Enemy item : Enemy.listEnemies)
				 player1.collisionPlayer(item);
				
			}
			
			/* colisões projeteis (player) - inimigos */
			for (Enemy itemEnemy : Enemy.listEnemies){
				for (Projectile item : Projectile.listProjectiles){
					itemEnemy.collisionEnemy(item);
				}
			}
				
			/***************************/
			/* Atualizações de estados */
			/***************************/
			
			/* projeteis (player e inimigos) */

			for (Projectile item : Projectile.listProjectiles)
				item.updateState();
			
			/* projeteis (inimigos) */
			
			
			/* inimigos tipo 1 */

			for (Enemy item : Enemy.listEnemies)
				item.readyToShoot(player1);

			/* verificando se novos inimigos devem ser "lançados" */
			
			for (Enemy element : Enemy.listEnemies)
				element.addEnemy();
		
			
			/* Verificando se a explosão do player já acabou.         */
			/* Ao final da explosão, o player volta a ser controlável */
			player1.hasExploded();
			
			/********************************************/
			/* Verificando entrada do usuário (teclado) */
			/********************************************/
			
			if(player1.getState() == ACTIVE){
				double playerY = player1.getY();
				double playerX = player1.getX();
				double playerVX = player1.getVelocityX();
				double playerVY = player1.getVelocityY();
				long delta = Game_Object.delta;
				
				
				if(GameLib.iskeyPressed(GameLib.KEY_UP)) playerY -= delta * playerVY;
				if(GameLib.iskeyPressed(GameLib.KEY_DOWN)) playerX += delta * playerVY;
				if(GameLib.iskeyPressed(GameLib.KEY_LEFT)) playerX -= delta * playerVX;
				if(GameLib.iskeyPressed(GameLib.KEY_RIGHT)) playerX += delta * playerVY;
				
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
			
			GameLib.setColor(Color.DARK_GRAY);
			
			bg1.visualBackground();
			bg2.visualBackground();

			/* desenhando player */
			
			player1.visualPlayer();
				
			/* deenhando projeteis (player) */
			/* desenhando projeteis (inimigos) */
			
			projectile_player.visualProjectile();
			projectile_enemy1.visualProjectile();
			
			
			/* desenhando inimigos (tipo 1) */
			/* desenhando inimigos (tipo 2) */
			
			enemy1.visualEnemies();
			enemy2.visualEnemies();
			
			
			/* chamada a display() da classe GameLib atualiza o desenho exibido pela interface do jogo. */
			
			GameLib.display();
			
			/* faz uma pausa de modo que cada execução do laço do main loop demore aproximadamente 3 ms. */
			
			busyWait(Game_Object.currentTime + 3);
		}
		
		System.exit(0);
	}
}