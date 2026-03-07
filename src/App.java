import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the hero's name: ");
        String nameHero = input.nextLine();

        System.out.print("Enter the hero's health: ");
        int healthHero = input.nextInt(); // vida total, se chegar em 0, acaba o jogo

        System.out.print("Enter the hero's energy: ");
        int energyHero = input.nextInt(); // energia total, é recarregada a cada rodada

        Hero hero = new Hero(nameHero, healthHero, energyHero, 0);

        System.out.print("Enter the enemy's name: ");
        String nameEnemy = input.nextLine();

        System.out.print("Enter the enemy's health: ");
        int healthEnemy = input.nextInt(); 

        Enemy enemy = new Enemy(nameEnemy, healthEnemy, 0);

        // instanciar uma carta de dano (ex.: carta espada) estabelecer o dano dela e o custo de energia 
        // fazer mais cartas de dano depois (pelo menos umas três)
        // instanciar uma carta de escudo e estabelecer quanto de defesa ela fornece
        // fazer mais cartas de escudo depois
        // um while que controla cada rodada
        // sempre que o inimigo ou o jogador tomarem dano, checar se eles ficaram sem vida (encerra o jogo)
        // sempre que o heroi gastar energia, checar se ela zerou (encerra o turno, tem apenas mais uma jogada da vez do inimigo que é automática) 
        // energia é recarregada a cada rodada
        // escuda volta para 0 a cada rodada
        // o heroi pode encerrar seu turno a qualquer momento
        // trabalhar na interface do jogo no terminal: na vez do jogador mostrar que cartas ele pode usar, sua vida e energia, sua defesa (escudo)
        // depois de conseguir rodar um jogo simples no qual o heroi e o inimigo se atacam até um deles ser derrotado: adicionar outras classes (relíquias, buffs...)
    }
}
