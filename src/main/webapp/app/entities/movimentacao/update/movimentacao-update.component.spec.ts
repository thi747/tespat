import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IBem } from 'app/entities/bem/bem.model';
import { BemService } from 'app/entities/bem/service/bem.service';
import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/service/pessoa.service';
import { IMovimentacao } from '../movimentacao.model';
import { MovimentacaoService } from '../service/movimentacao.service';
import { MovimentacaoFormService } from './movimentacao-form.service';

import { MovimentacaoUpdateComponent } from './movimentacao-update.component';

describe('Movimentacao Management Update Component', () => {
  let comp: MovimentacaoUpdateComponent;
  let fixture: ComponentFixture<MovimentacaoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let movimentacaoFormService: MovimentacaoFormService;
  let movimentacaoService: MovimentacaoService;
  let bemService: BemService;
  let pessoaService: PessoaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), MovimentacaoUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(MovimentacaoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MovimentacaoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    movimentacaoFormService = TestBed.inject(MovimentacaoFormService);
    movimentacaoService = TestBed.inject(MovimentacaoService);
    bemService = TestBed.inject(BemService);
    pessoaService = TestBed.inject(PessoaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Bem query and add missing value', () => {
      const movimentacao: IMovimentacao = { id: 456 };
      const bem: IBem = { patrimonio: 32110 };
      movimentacao.bem = bem;

      const bemCollection: IBem[] = [{ patrimonio: 4535 }];
      jest.spyOn(bemService, 'query').mockReturnValue(of(new HttpResponse({ body: bemCollection })));
      const additionalBems = [bem];
      const expectedCollection: IBem[] = [...additionalBems, ...bemCollection];
      jest.spyOn(bemService, 'addBemToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ movimentacao });
      comp.ngOnInit();

      expect(bemService.query).toHaveBeenCalled();
      expect(bemService.addBemToCollectionIfMissing).toHaveBeenCalledWith(bemCollection, ...additionalBems.map(expect.objectContaining));
      expect(comp.bemsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Pessoa query and add missing value', () => {
      const movimentacao: IMovimentacao = { id: 456 };
      const pessoa: IPessoa = { usuario: '9cf41d98-5f9b-4873-a0cc-81ee6cdc70a6' };
      movimentacao.pessoa = pessoa;

      const pessoaCollection: IPessoa[] = [{ usuario: '8b637a0c-755c-4a3f-8251-d93d3cbce97a' }];
      jest.spyOn(pessoaService, 'query').mockReturnValue(of(new HttpResponse({ body: pessoaCollection })));
      const additionalPessoas = [pessoa];
      const expectedCollection: IPessoa[] = [...additionalPessoas, ...pessoaCollection];
      jest.spyOn(pessoaService, 'addPessoaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ movimentacao });
      comp.ngOnInit();

      expect(pessoaService.query).toHaveBeenCalled();
      expect(pessoaService.addPessoaToCollectionIfMissing).toHaveBeenCalledWith(
        pessoaCollection,
        ...additionalPessoas.map(expect.objectContaining),
      );
      expect(comp.pessoasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const movimentacao: IMovimentacao = { id: 456 };
      const bem: IBem = { patrimonio: 6810 };
      movimentacao.bem = bem;
      const pessoa: IPessoa = { usuario: '65cfbd3b-e220-49e7-8d4a-d831b2807662' };
      movimentacao.pessoa = pessoa;

      activatedRoute.data = of({ movimentacao });
      comp.ngOnInit();

      expect(comp.bemsSharedCollection).toContain(bem);
      expect(comp.pessoasSharedCollection).toContain(pessoa);
      expect(comp.movimentacao).toEqual(movimentacao);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMovimentacao>>();
      const movimentacao = { id: 123 };
      jest.spyOn(movimentacaoFormService, 'getMovimentacao').mockReturnValue(movimentacao);
      jest.spyOn(movimentacaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ movimentacao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: movimentacao }));
      saveSubject.complete();

      // THEN
      expect(movimentacaoFormService.getMovimentacao).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(movimentacaoService.update).toHaveBeenCalledWith(expect.objectContaining(movimentacao));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMovimentacao>>();
      const movimentacao = { id: 123 };
      jest.spyOn(movimentacaoFormService, 'getMovimentacao').mockReturnValue({ id: null });
      jest.spyOn(movimentacaoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ movimentacao: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: movimentacao }));
      saveSubject.complete();

      // THEN
      expect(movimentacaoFormService.getMovimentacao).toHaveBeenCalled();
      expect(movimentacaoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMovimentacao>>();
      const movimentacao = { id: 123 };
      jest.spyOn(movimentacaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ movimentacao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(movimentacaoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareBem', () => {
      it('Should forward to bemService', () => {
        const entity = { patrimonio: 123 };
        const entity2 = { patrimonio: 456 };
        jest.spyOn(bemService, 'compareBem');
        comp.compareBem(entity, entity2);
        expect(bemService.compareBem).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePessoa', () => {
      it('Should forward to pessoaService', () => {
        const entity = { usuario: 'ABC' };
        const entity2 = { usuario: 'CBA' };
        jest.spyOn(pessoaService, 'comparePessoa');
        comp.comparePessoa(entity, entity2);
        expect(pessoaService.comparePessoa).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
