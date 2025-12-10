package ifsc.joe.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ifsc.joe.domain.impl.Aldeao;
import ifsc.joe.domain.impl.Arqueiro;
import ifsc.joe.domain.impl.Cavaleiro;

public class GameEngine {

    private List<Personagem> personagens;
    private int nextId = 1;
    private final Random random;

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
        personagens.removeIf(p -> !p.isVivo());
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