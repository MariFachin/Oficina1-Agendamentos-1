import { IAgendaServidor } from 'app/shared/model/agenda-servidor.model';
import { IAluno } from 'app/shared/model/aluno.model';
import { StatusAgenda } from 'app/shared/model/enumerations/status-agenda.model';

export interface IAgendaAtendimentoServidor {
  id?: number;
  status?: StatusAgenda;
  codSiapes?: IAgendaServidor[];
  horarios?: IAgendaServidor[];
  mes?: IAgendaServidor[];
  diaMes?: IAgendaServidor[];
  statuses?: IAgendaServidor[];
  raAlunos?: IAluno[];
}

export class AgendaAtendimentoServidor implements IAgendaAtendimentoServidor {
  constructor(
    public id?: number,
    public status?: StatusAgenda,
    public codSiapes?: IAgendaServidor[],
    public horarios?: IAgendaServidor[],
    public mes?: IAgendaServidor[],
    public diaMes?: IAgendaServidor[],
    public statuses?: IAgendaServidor[],
    public raAlunos?: IAluno[]
  ) {}
}