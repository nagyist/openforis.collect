import {Injectable}     from "@angular/core";

import { Survey }       from './metamodel/survey';

@Injectable()
export class CollectGlobals {
	activeSurvey: Survey;
}