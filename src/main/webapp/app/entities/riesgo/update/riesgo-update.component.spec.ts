import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IGrupoActivos } from 'app/entities/grupo-activos/grupo-activos.model';
import { GrupoActivosService } from 'app/entities/grupo-activos/service/grupo-activos.service';
import { IControles } from 'app/entities/controles/controles.model';
import { ControlesService } from 'app/entities/controles/service/controles.service';
import { IAmenazas } from 'app/entities/amenazas/amenazas.model';
import { AmenazasService } from 'app/entities/amenazas/service/amenazas.service';
import { IVulnerabilidades } from 'app/entities/vulnerabilidades/vulnerabilidades.model';
import { VulnerabilidadesService } from 'app/entities/vulnerabilidades/service/vulnerabilidades.service';
import { IRiesgo } from '../riesgo.model';
import { RiesgoService } from '../service/riesgo.service';
import { RiesgoFormService } from './riesgo-form.service';

import { RiesgoUpdateComponent } from './riesgo-update.component';

describe('Riesgo Management Update Component', () => {
  let comp: RiesgoUpdateComponent;
  let fixture: ComponentFixture<RiesgoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let riesgoFormService: RiesgoFormService;
  let riesgoService: RiesgoService;
  let grupoActivosService: GrupoActivosService;
  let controlesService: ControlesService;
  let amenazasService: AmenazasService;
  let vulnerabilidadesService: VulnerabilidadesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RiesgoUpdateComponent],
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
      .overrideTemplate(RiesgoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RiesgoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    riesgoFormService = TestBed.inject(RiesgoFormService);
    riesgoService = TestBed.inject(RiesgoService);
    grupoActivosService = TestBed.inject(GrupoActivosService);
    controlesService = TestBed.inject(ControlesService);
    amenazasService = TestBed.inject(AmenazasService);
    vulnerabilidadesService = TestBed.inject(VulnerabilidadesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call GrupoActivos query and add missing value', () => {
      const riesgo: IRiesgo = { id: 456 };
      const activos: IGrupoActivos[] = [{ id: 13598 }];
      riesgo.activos = activos;

      const grupoActivosCollection: IGrupoActivos[] = [{ id: 12705 }];
      jest.spyOn(grupoActivosService, 'query').mockReturnValue(of(new HttpResponse({ body: grupoActivosCollection })));
      const additionalGrupoActivos = [...activos];
      const expectedCollection: IGrupoActivos[] = [...additionalGrupoActivos, ...grupoActivosCollection];
      jest.spyOn(grupoActivosService, 'addGrupoActivosToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ riesgo });
      comp.ngOnInit();

      expect(grupoActivosService.query).toHaveBeenCalled();
      expect(grupoActivosService.addGrupoActivosToCollectionIfMissing).toHaveBeenCalledWith(
        grupoActivosCollection,
        ...additionalGrupoActivos.map(expect.objectContaining),
      );
      expect(comp.grupoActivosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Controles query and add missing value', () => {
      const riesgo: IRiesgo = { id: 456 };
      const controles: IControles[] = [{ id: 27351 }];
      riesgo.controles = controles;

      const controlesCollection: IControles[] = [{ id: 30016 }];
      jest.spyOn(controlesService, 'query').mockReturnValue(of(new HttpResponse({ body: controlesCollection })));
      const additionalControles = [...controles];
      const expectedCollection: IControles[] = [...additionalControles, ...controlesCollection];
      jest.spyOn(controlesService, 'addControlesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ riesgo });
      comp.ngOnInit();

      expect(controlesService.query).toHaveBeenCalled();
      expect(controlesService.addControlesToCollectionIfMissing).toHaveBeenCalledWith(
        controlesCollection,
        ...additionalControles.map(expect.objectContaining),
      );
      expect(comp.controlesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Amenazas query and add missing value', () => {
      const riesgo: IRiesgo = { id: 456 };
      const amenaza: IAmenazas = { id: 23314 };
      riesgo.amenaza = amenaza;

      const amenazasCollection: IAmenazas[] = [{ id: 4626 }];
      jest.spyOn(amenazasService, 'query').mockReturnValue(of(new HttpResponse({ body: amenazasCollection })));
      const additionalAmenazas = [amenaza];
      const expectedCollection: IAmenazas[] = [...additionalAmenazas, ...amenazasCollection];
      jest.spyOn(amenazasService, 'addAmenazasToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ riesgo });
      comp.ngOnInit();

      expect(amenazasService.query).toHaveBeenCalled();
      expect(amenazasService.addAmenazasToCollectionIfMissing).toHaveBeenCalledWith(
        amenazasCollection,
        ...additionalAmenazas.map(expect.objectContaining),
      );
      expect(comp.amenazasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Vulnerabilidades query and add missing value', () => {
      const riesgo: IRiesgo = { id: 456 };
      const vulnerabilidad: IVulnerabilidades = { id: 28486 };
      riesgo.vulnerabilidad = vulnerabilidad;

      const vulnerabilidadesCollection: IVulnerabilidades[] = [{ id: 4244 }];
      jest.spyOn(vulnerabilidadesService, 'query').mockReturnValue(of(new HttpResponse({ body: vulnerabilidadesCollection })));
      const additionalVulnerabilidades = [vulnerabilidad];
      const expectedCollection: IVulnerabilidades[] = [...additionalVulnerabilidades, ...vulnerabilidadesCollection];
      jest.spyOn(vulnerabilidadesService, 'addVulnerabilidadesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ riesgo });
      comp.ngOnInit();

      expect(vulnerabilidadesService.query).toHaveBeenCalled();
      expect(vulnerabilidadesService.addVulnerabilidadesToCollectionIfMissing).toHaveBeenCalledWith(
        vulnerabilidadesCollection,
        ...additionalVulnerabilidades.map(expect.objectContaining),
      );
      expect(comp.vulnerabilidadesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const riesgo: IRiesgo = { id: 456 };
      const activos: IGrupoActivos = { id: 4262 };
      riesgo.activos = [activos];
      const controles: IControles = { id: 27433 };
      riesgo.controles = [controles];
      const amenaza: IAmenazas = { id: 6630 };
      riesgo.amenaza = amenaza;
      const vulnerabilidad: IVulnerabilidades = { id: 5597 };
      riesgo.vulnerabilidad = vulnerabilidad;

      activatedRoute.data = of({ riesgo });
      comp.ngOnInit();

      expect(comp.grupoActivosSharedCollection).toContain(activos);
      expect(comp.controlesSharedCollection).toContain(controles);
      expect(comp.amenazasSharedCollection).toContain(amenaza);
      expect(comp.vulnerabilidadesSharedCollection).toContain(vulnerabilidad);
      expect(comp.riesgo).toEqual(riesgo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRiesgo>>();
      const riesgo = { id: 123 };
      jest.spyOn(riesgoFormService, 'getRiesgo').mockReturnValue(riesgo);
      jest.spyOn(riesgoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ riesgo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: riesgo }));
      saveSubject.complete();

      // THEN
      expect(riesgoFormService.getRiesgo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(riesgoService.update).toHaveBeenCalledWith(expect.objectContaining(riesgo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRiesgo>>();
      const riesgo = { id: 123 };
      jest.spyOn(riesgoFormService, 'getRiesgo').mockReturnValue({ id: null });
      jest.spyOn(riesgoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ riesgo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: riesgo }));
      saveSubject.complete();

      // THEN
      expect(riesgoFormService.getRiesgo).toHaveBeenCalled();
      expect(riesgoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRiesgo>>();
      const riesgo = { id: 123 };
      jest.spyOn(riesgoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ riesgo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(riesgoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareGrupoActivos', () => {
      it('Should forward to grupoActivosService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(grupoActivosService, 'compareGrupoActivos');
        comp.compareGrupoActivos(entity, entity2);
        expect(grupoActivosService.compareGrupoActivos).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareControles', () => {
      it('Should forward to controlesService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(controlesService, 'compareControles');
        comp.compareControles(entity, entity2);
        expect(controlesService.compareControles).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareAmenazas', () => {
      it('Should forward to amenazasService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(amenazasService, 'compareAmenazas');
        comp.compareAmenazas(entity, entity2);
        expect(amenazasService.compareAmenazas).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareVulnerabilidades', () => {
      it('Should forward to vulnerabilidadesService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(vulnerabilidadesService, 'compareVulnerabilidades');
        comp.compareVulnerabilidades(entity, entity2);
        expect(vulnerabilidadesService.compareVulnerabilidades).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
