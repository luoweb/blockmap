#include "FHEContext.h"
#include "EncryptedArray.h"
#include "FHE.h"
#include "DoubleCRT.h"

#include <stack>
#include <string>
#include <stdlib.h>


stack<Ctxt> theStack;
FHEcontext* context;
FHESecKey* secretKey;
FHEPubKey* publicKey;
EncryptedArray* ea;
ZZX G;

void setupHElib();
bool isOp(string token);
void evaluate(char op);

void greeting(){
  cout <<"Welcome to the homomorphic encryption calculator" <<endl;
  cout <<"Enter expression in reverse polish natation"<<endl;
  cout <<"Enter q to quit"<<endl; 
}


int main(int argc, char** argv){

  string token;  

  greeting();
  setupHElib();
  ea = new EncryptedArray(*context, G);

  while(true){

    cin >> token;

    if(token[0] == 'q'){
      break;
    }
    else if(isOp(token)){
      if(theStack.size()<2){
        cout << "not enough numbers on the stack"<<endl;
      }
      else{
        evaluate(token[0]);
      }
    }
    else{
      Ctxt& c0= *(new Ctxt(*publicKey));
      NewPlaintextArray p0(*ea);
      encode(*ea,p0,atoi(token.data()));
      ea->encrypt(c0, *publicKey, p0);

      theStack.push(c0);
    }
  }

  NewPlaintextArray p_decrypted(*ea);
  ea->decrypt(theStack.top(), *secretKey, p_decrypted);
  cout << "The answer is: ";
  p_decrypted.print(cout);
  cout << endl;

}


void setupHElib(){
  long p=101;
  long r=1;
  long L=4;
  long c=2;
  long k=80;
  long s=0;
  long d=0;
  long w=64;
  long m=FindM(k,L,c,p,d,s,0);

  context = new FHEcontext(m,p,r);
  buildModChain(*context, L, c);
  G = context->alMod.getFactorsOverZZ()[0];

  secretKey = new FHESecKey(*context);
  publicKey = secretKey;

  secretKey->GenSecKey(w);
  addSome1DMatrices(*secretKey); // compute key-switching matrices that we need
}

bool isOp(string token){
  return (token[0] == '+' || token[0] == '-' || token[0] == '*');
}

void evaluate(char op){
  Ctxt *op1,*op2;

  switch(op) {
        case '+':
            op1 = new Ctxt(theStack.top()); theStack.pop();
            op2 = new Ctxt(theStack.top()); theStack.pop();
      (*op1) += (*op2);
            theStack.push(*op1);
            break;
        case '-':
            op1 = new Ctxt(theStack.top()); theStack.pop();
            op2 = new Ctxt(theStack.top()); theStack.pop();
      (*op1) -= (*op2);
            theStack.push(*op1);
            break;
    case '*':
            op1 = new Ctxt(theStack.top()); theStack.pop();
            op2 = new Ctxt(theStack.top()); theStack.pop();
      (*op1) *= (*op2);
            theStack.push(*op1);
            break;
    }
}
