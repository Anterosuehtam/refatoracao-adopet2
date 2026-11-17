package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class ValidacaoPetDisponivelTest {

    @InjectMocks
    private ValidacaoPetDisponivel validacao;

    @Mock
    private PetRepository petRepository;

    @Test
    void deveriaPermitirSolicitacaoDeAdocaoPet() {
        // ARRANGE
        // Instancia o DTO real (ajuste os valores conforme os atributos do seu record)
        SolicitacaoAdocaoDto dto = new SolicitacaoAdocaoDto(1L, 1L, "Motivo teste");

        Pet pet = new Pet();
        ReflectionTestUtils.setField(pet, "adotado", false);

        BDDMockito.given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);

        // ASSERT + ACT
        Assertions.assertDoesNotThrow(() -> validacao.validar(dto));
    }

    @Test
    void naoDeveriaPermitirSolicitacaoDeAdocaoPet() {
        // ARRANGE
        SolicitacaoAdocaoDto dto = new SolicitacaoAdocaoDto(1L, 1L, "Motivo teste");

        Pet pet = new Pet();
        ReflectionTestUtils.setField(pet, "adotado", true);

        BDDMockito.given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);

        // ASSERT + ACT
        Assertions.assertThrows(ValidacaoException.class, () -> validacao.validar(dto));
    }
}