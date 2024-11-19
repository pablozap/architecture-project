import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IRiesgo } from 'app/entities/riesgo/riesgo.model';
import { RiesgoService } from 'app/entities/riesgo/service/riesgo.service';
import { GrupoActivosService } from '../service/grupo-activos.service';
import { IGrupoActivos } from '../grupo-activos.model';
import { GrupoActivosFormService } from './grupo-activos-form.service';

import { GrupoActivosUpdateComponent } from './grupo-activos-update.component';

describe('GrupoActivos Management Update Component', () => {
  let comp: GrupoActivosUpdateComponent;
  let fixture: ComponentFixture<GrupoActivosUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let grupoActivosFormService: GrupoActivosFormService;
  let grupoActivosService: GrupoActivosService;
  let riesgoService: RiesgoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [GrupoActivosUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(GrupoActivosUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GrupoActivosUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    grupoActivosFormService = TestBed.inject(GrupoActivosFormService);
    grupoActivosService = TestBed.inject(GrupoActivosService);
    riesgoService = TestBed.inject(RiesgoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Riesgo query and add missing value', () => {
      const grupoActivos: IGrupoActivos = { id: 456 };
      const riesgos: IRiesgo[] = [{ id: 30603 }];
      grupoActivos.riesgos = riesgos;

      const riesgoCollection: IRiesgo[] = [{ id: 13496 }];
      jest.spyOn(riesgoService, 'query').mockReturnValue(of(new HttpResponse({ body: riesgoCollection })));
      const additionalRiesgos = [...riesgos];
      const expectedCollection: IRiesgo[] = [...additionalRiesgos, ...riesgoCollection];
      jest.spyOn(riesgoService, 'addRiesgoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ grupoActivos });
      comp.ngOnInit();

      expect(riesgoService.query).toHaveBeenCalled();
      expect(riesgoService.addRiesgoToCollectionIfMissing).toHaveBeenCalledWith(
        riesgoCollection,
        ...additionalRiesgos.map(expect.objectContaining),
      );
      expect(comp.riesgosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const grupoActivos: IGrupoActivos = { id: 456 };
      const riesgos: IRiesgo = { id: 155 };
      grupoActivos.riesgos = [riesgos];

      activatedRoute.data = of({ grupoActivos });
      comp.ngOnInit();

      expect(comp.riesgosSharedCollection).toContain(riesgos);
      expect(comp.grupoActivos).toEqual(grupoActivos);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGrupoActivos>>();
      const grupoActivos = { id: 123 };
      jest.spyOn(grupoActivosFormService, 'getGrupoActivos').mockReturnValue(grupoActivos);
      jest.spyOn(grupoActivosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ grupoActivos });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: grupoActivos }));
      saveSubject.complete();

      // THEN
      expect(grupoActivosFormService.getGrupoActivos).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(grupoActivosService.update).toHaveBeenCalledWith(expect.objectContaining(grupoActivos));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGrupoActivos>>();
      const grupoActivos = { id: 123 };
      jest.spyOn(grupoActivosFormService, 'getGrupoActivos').mockReturnValue({ id: null });
      jest.spyOn(grupoActivosService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ grupoActivos: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: grupoActivos }));
      saveSubject.complete();

      // THEN
      expect(grupoActivosFormService.getGrupoActivos).toHaveBeenCalled();
      expect(grupoActivosService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGrupoActivos>>();
      const grupoActivos = { id: 123 };
      jest.spyOn(grupoActivosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ grupoActivos });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(grupoActivosService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareRiesgo', () => {
      it('Should forward to riesgoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(riesgoService, 'compareRiesgo');
        comp.compareRiesgo(entity, entity2);
        expect(riesgoService.compareRiesgo).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
