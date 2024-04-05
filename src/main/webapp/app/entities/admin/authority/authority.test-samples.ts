import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: '45cf9d2f-1515-4352-acad-aed2bd830d04',
};

export const sampleWithPartialData: IAuthority = {
  name: '4d7bfe90-4422-4a1a-855d-e0b66d58dc6b',
};

export const sampleWithFullData: IAuthority = {
  name: '831d7108-2876-44fb-bb86-739251a67268',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
