import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Aluno } from 'app/shared/model/aluno.model';
import { AlunoService } from './aluno.service';
import { AlunoComponent } from './aluno.component';
import { AlunoDetailComponent } from './aluno-detail.component';
import { AlunoUpdateComponent } from './aluno-update.component';
import { AlunoDeletePopupComponent } from './aluno-delete-dialog.component';
import { IAluno } from 'app/shared/model/aluno.model';

@Injectable({ providedIn: 'root' })
export class AlunoResolve implements Resolve<IAluno> {
  constructor(private service: AlunoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAluno> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Aluno>) => response.ok),
        map((aluno: HttpResponse<Aluno>) => aluno.body)
      );
    }
    return of(new Aluno());
  }
}

export const alunoRoute: Routes = [
  {
    path: '',
    component: AlunoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Alunos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AlunoDetailComponent,
    resolve: {
      aluno: AlunoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Alunos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AlunoUpdateComponent,
    resolve: {
      aluno: AlunoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Alunos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AlunoUpdateComponent,
    resolve: {
      aluno: AlunoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Alunos'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const alunoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AlunoDeletePopupComponent,
    resolve: {
      aluno: AlunoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Alunos'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
