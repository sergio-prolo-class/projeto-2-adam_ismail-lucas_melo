package ifsc.joe.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ifsc.joe.domain.impl.Aldeao;
import ifsc.joe.domain.impl.Arqueiro;
import ifsc.joe.domain.impl.Cavaleiro;



public class GameEngine {

    private List<Personagem> personagens; // lista de personagens no jogo
    private int nextId = 1; // próximo ID disponível
    private final Random random; // gerador de números aleatórios
    private int mortesAldeao = 0; // contador de mortes por tipo
    private int mortesArqueiro = 0;
    private int mortesCavaleiro = 0;


    public GameEngine() {
        this.personagens = new ArrayList<>();
        this.random = new Random();
    }

    public void adicionarPersonagem(Personagem p) {
        p.setId(nextId++);
        personagens.add(p);
    }

    public int getNextId() {
        return nextId;
    }

    public void removerMortos() {
        personagens.removeIf(p -> {
            if (!p.isVivo()) {
                
                if (p instanceof ifsc.joe.domain.impl.Aldeao) mortesAldeao++;
                if (p instanceof ifsc.joe.domain.impl.Arqueiro) mortesArqueiro++;
                if (p instanceof ifsc.joe.domain.impl.Cavaleiro) mortesCavaleiro++;

                System.out.println("Morto: " + p.getClass().getSimpleName());
                return true;
            }
            return false;
        });
    }

    public List<Personagem> getPersonagens() {
        return personagens;
    }

    public void atualizarTodos() {
        for (Personagem p : personagens) {
            p.atualizar();
        }
        removerMortos();
    }

    public Personagem gerarAleatorio(int x, int y) {

        int escolha = random.nextInt(3);
        Personagem p;

        switch (escolha) {
            case 0 -> p = new Aldeao(x, y, nextId);
            case 1 -> p = new Arqueiro(x, y, nextId);
            case 2 -> p = new Cavaleiro(x, y, nextId);
            default -> throw new IllegalStateException("Erro ao gerar personagem aleatório");
        }

        adicionarPersonagem(p);
        return p;
    }

    public void atacarGuerreiros(int distancia) {
    for (Personagem atacante : personagens) {

        // só guerreiros atacam
        if (!(atacante instanceof ifsc.joe.domain.interfaces.Lutador)) {
            continue;
        }

        atacante.setAtacando(true);

        for (Personagem alvo : personagens) {
            if (alvo == atacante) continue;
            if (!alvo.isVivo()) continue;

            // calcular distância
            double dx = atacante.getX() - alvo.getX();
            double dy = atacante.getY() - alvo.getY();
            double dist = Math.sqrt(dx*dx + dy*dy);

            if (dist <= distancia) {
                alvo.receberDano(atacante.getAtaque());
            }
        }
    }

    removerMortos();
}

}