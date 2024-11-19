import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IVulnerabilidades } from '../vulnerabilidades.model';
import { VulnerabilidadesService } from '../service/vulnerabilidades.service';
import { VulnerabilidadesFormGroup, VulnerabilidadesFormService } from './vulnerabilidades-form.service';

@Component({
  standalone: true,
  selector: 'jhi-vulnerabilidades-update',
  templateUrl: './vulnerabilidades-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class VulnerabilidadesUpdateComponent implements OnInit {
  isSaving = false;
  vulnerabilidades: IVulnerabilidades | null = null;

  protected vulnerabilidadesService = inject(VulnerabilidadesService);
  protected vulnerabilidadesFormService = inject(VulnerabilidadesFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: VulnerabilidadesFormGroup = this.vulnerabilidadesFormService.createVulnerabilidadesFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vulnerabilidades }) => {
      this.vulnerabilidades = vulnerabilidades;
      if (vulnerabilidades) {
        this.updateForm(vulnerabilidades);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vulnerabilidades = this.vulnerabilidadesFormService.getVulnerabilidades(this.editForm);
    if (vulnerabilidades.id !== null) {
      this.subscribeToSaveResponse(this.vulnerabilidadesService.update(vulnerabilidades));
    } else {
      this.subscribeToSaveResponse(this.vulnerabilidadesService.create(vulnerabilidades));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVulnerabilidades>>): void {
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

  protected updateForm(vulnerabilidades: IVulnerabilidades): void {
    this.vulnerabilidades = vulnerabilidades;
    this.vulnerabilidadesFormService.resetForm(this.editForm, vulnerabilidades);
  }
}
