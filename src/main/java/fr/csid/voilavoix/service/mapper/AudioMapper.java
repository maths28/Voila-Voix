package fr.csid.voilavoix.service.mapper;

import fr.csid.voilavoix.domain.*;
import fr.csid.voilavoix.service.dto.AudioDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Audio and its DTO AudioDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AudioMapper {

    AudioDTO audioToAudioDTO(Audio audio);

    List<AudioDTO> audiosToAudioDTOs(List<Audio> audios);

    Audio audioDTOToAudio(AudioDTO audioDTO);

    List<Audio> audioDTOsToAudios(List<AudioDTO> audioDTOs);
}
