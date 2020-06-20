/*
    Created on 20/06/2020 by uex1421
 */
package michal.zawadzki.workdayappui.mapper;

import java.util.Optional;
import michal.zawadzki.workdayappclient.api.worker.OccupationDto;
import michal.zawadzki.workdayappclient.api.worker.SimplifiedWorkerDto;
import michal.zawadzki.workdayappclient.api.worker.WorkerDto;
import michal.zawadzki.workdayappui.model.WorkerPresentationView;

public class WorkerPresentationMapper {

    private static final String BLANK = "";

    private WorkerPresentationMapper() {

    }

    public static WorkerPresentationView toPresentationView(WorkerDto workerDto) {
        final Optional<OccupationDto> currentPosition = Optional.ofNullable(workerDto.getCurrentPosition());
        final Optional<SimplifiedWorkerDto> supervisor = Optional.ofNullable(workerDto.getSupervisor());
        return WorkerPresentationView.builder()
                .id(workerDto.getId())
                .role(workerDto.getRole().toString())
                .email(workerDto.getEmail())
                .firstName(workerDto.getFirstName())
                .lastName(workerDto.getLastName())
                .position(currentPosition.map(occupationDto -> occupationDto.getPosition().getName()).orElse(BLANK))
                .team(currentPosition.map(occupationDto -> occupationDto.getTeam().getName()).orElse(BLANK))
                .department(currentPosition.map(occupationDto -> occupationDto.getDepartment().getName()).orElse(BLANK))
                .supervisor(supervisor.map(WorkerPresentationMapper::getFullName).orElse(BLANK))
                .build();
    }

    private static String getFullName(SimplifiedWorkerDto simplifiedWorkerDto) {
        return simplifiedWorkerDto.getFirstName() + " " + simplifiedWorkerDto.getLastName();
    }

}
