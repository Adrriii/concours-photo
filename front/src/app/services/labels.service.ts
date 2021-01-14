import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Label} from '../models/Label.model';
import {Subject} from 'rxjs';
import {environment} from '../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class LabelsService {
    private labels: Array<Label>;
    public labelsSubject = new Subject<Array<Label>>();

    constructor(
        private httpClient: HttpClient
    ) {
    }

    public emit(): void {
        this.labelsSubject.next(this.labels);
    }

    public getAll(): void {
        this.httpClient.get<Array<Label>>(environment.apiBaseUrl + 'labels').subscribe(
            labels => {
                this.labels = new Array<Label>();
                this.labels.push(...labels);
                this.emit();
            }
        );
    }
}
