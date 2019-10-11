import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAgendaAluno } from 'app/shared/model/agenda-aluno.model';

type EntityResponseType = HttpResponse<IAgendaAluno>;
type EntityArrayResponseType = HttpResponse<IAgendaAluno[]>;

@Injectable({ providedIn: 'root' })
export class AgendaAlunoService {
  public resourceUrl = SERVER_API_URL + 'api/agenda-alunos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/agenda-alunos';

  constructor(protected http: HttpClient) {}

  create(agendaAluno: IAgendaAluno): Observable<EntityResponseType> {
    return this.http.post<IAgendaAluno>(this.resourceUrl, agendaAluno, { observe: 'response' });
  }

  update(agendaAluno: IAgendaAluno): Observable<EntityResponseType> {
    return this.http.put<IAgendaAluno>(this.resourceUrl, agendaAluno, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAgendaAluno>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAgendaAluno[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAgendaAluno[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
