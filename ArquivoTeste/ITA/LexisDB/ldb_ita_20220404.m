function ldb_ita()
global nofluctM nofluctF

nofluctM=1872:1900;
nofluctF=1872:1900;

indb_input('ITA');
load ITA;
deaths=selif(deaths, deaths(:, end)==1);
deaths=deaths(:,1:end-1);
population=selif(population, population(:, end)==1);
p=population(:,1:end-1);
births=selif(births, births(:, end)==1);
births=births(:,1:end-1);
deaths=selif(deaths, deaths(:,4)~=0 | deaths(:,7)~=5);

d=selif(deaths, deaths(:,3)>=1938 & deaths(:,3)<=1940);
d1=selif(d, d(:,5)~=3);
d2=selif(d, d(:,5)==3);
save rsd1
d1=d_s5x1vv(d1);
d1=d_svv(d1);
d2=d_s5x1(d2);
d2=d_s1x1(d2,births,d1);
d1=d_sum(d1, d2);%!!

d=selif(deaths, deaths(:,3)<1938 | deaths(:,3)>1940);
d=[d1; d];
clear deaths;

d=d_ma0(d);

d=d_long(d);


d=d_s5x1(d);

disp('RR-> TL+TU');

d1=selif(d,d(:,5)==3);
d=delif(d,d(:,5)==3);


disp('split RR');
d1=d_s1x1tn(d1, births, tadj,d);
d=d_sum(d,d1);
save rsd4

d=d_soainew(d,1900);
disp('distribution of unknown');
d=d_unk(d);



disp('population');
disp('read...');


disp('Intercensal');
%Canonical code, commented out
%p=p_unk(p);
%save rs1;
%p=p_ict(p, d, births, tadj);
% post censal estimate added by Celeste 8/14/2015
%p = p_postcensal(p,d,births,[2012 2013]);
%disp('Extinct cohort method');
%p=p_srecmt(p, d, tadj);


% Tim's proposed updated code (Aug 18, 2015)
p=p_unk(p);
save rs1;
pright = selif(p,p(:,5) >= 1982);   
dright = selif(d,d(:,3) >= 1982);
pleft = selif(p,p(:,5) < 1982);
pright = p_srecm(pright, dright);
p = [pleft; pright]             % or whatever rbind() is in matlab, ask Celeste
p=p_ict(p, d, births, tadj);
% p=p_postcensal(p,d,births,[2017,2018]);
disp('Extinct cohort method');
p=p_srecmt(p, d, tadj);


ldb_output(d, p, 'mITA.txt', 'fITA.txt', births);
d_printRA('ITA','Italy');
