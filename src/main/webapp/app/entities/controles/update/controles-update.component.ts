import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IRiesgo } from 'app/entities/riesgo/riesgo.model';
import { RiesgoService } from 'app/entities/riesgo/service/riesgo.service';
import { IControles } from '../controles.model';
import { ControlesService } from '../service/controles.service';
import { ControlesFormGroup, ControlesFormService } from './controles-form.service';

@Component({
  standalone: true,
  selector: 'jhi-controles-update',
  templateUrl: './controles-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ControlesUpdateComponent implements OnInit {
  isSaving = false;
  controles: IControles | null = null;

  riesgosSharedCollection: IRiesgo[] = [];

  protected controlesService = inject(ControlesService);
  protected controlesFormService = inject(ControlesFormService);
  protected riesgoService = inject(RiesgoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ControlesFormGroup = this.controlesFormService.createControlesFormGroup();

  compareRiesgo = (o1: IRiesgo | null, o2: IRiesgo | null): boolean => this.riesgoService.compareRiesgo(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ controles }) => {
      this.controles = controles;
      if (controles) {
        this.updateForm(controles);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const controles = this.controlesFormService.getControles(this.editForm);
    if (controles.id !== null) {
      this.subscribeToSaveResponse(this.controlesService.update(controles));
    } else {
      this.subscribeToSaveResponse(this.controlesService.create(controles));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IControles>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(controles: IControles): void {
    this.controles = controles;
    this.controlesFormService.resetForm(this.editForm, controles);

    this.riesgosSharedCollection = this.riesgoService.addRiesgoToCollectionIfMissing<IRiesgo>(
      this.riesgosSharedCollection,
      ...(controles.riesgos ?? []),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.riesgoService
      .query()
      .pipe(map((res: HttpResponse<IRiesgo[]>) => res.body ?? []))
      .pipe(
        map((riesgos: IRiesgo[]) =>
          this.riesgoService.addRiesgoToCollectionIfMissing<IRiesgo>(riesgos, ...(this.controles?.riesgos ?? [])),
        ),
      )
      .subscribe((riesgos: IRiesgo[]) => (this.riesgosSharedCollection = riesgos));
  }
}
