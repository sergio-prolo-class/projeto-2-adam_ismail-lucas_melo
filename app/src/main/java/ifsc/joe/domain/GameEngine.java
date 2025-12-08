package ifsc.joe.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ifsc.joe.domain.impl.Aldeao;
import ifsc.joe.domain.impl.Arqueiro;
import ifsc.joe.domain.impl.Cavaleiro;
import ifsc.joe.domain.impl.Guerreiro;

public class GameEngine 
{
    private List<Personagem> personagens;
    private int nextId = 1;
    private final Random random;

    public GameEngine() 
    {
        this.personagens = new ArrayList<>();
        this.random = new Random();
    }

    public void adicionarPersonagem(Personagem p) 
    {
        p.setId(nextId++);
        personagens.add(p);
    }

    public void removerMortos()
    {
        personagens.removeIf(p -> !p.isVivo());
    }

    public List<Personagem> getPersonagens() 
    {
        return personagens;
    }

    public void atualizarTodos() 
    {
        for (Personagem p : personagens) 
        {
            p.atualizar();
        }
        removerMortos();
    }

    public Personagem gerarAleatorio(int x, int y) 
    {
        int escolha = random.nextInt(4);
        
        Personagem p;

        switch (escolha) 
        {
            case 0:
                p = new Aldeao(x, y);
                break;
            case 1:
                p = new Arqueiro(x, y);
                break;
            case 2:
                p = new Cavaleiro(x, y);
                break;
            case 3:
                p = new Guerreiro(x, y);
                break;
            default:
                throw new IllegalStateException("Erro ao gerar personagem aleatório");
        }

        adicionarPersonagem(p);
        return p;
    }
}
