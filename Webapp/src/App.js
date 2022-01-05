import ProtoStatements from './protos/statements';
import axios from 'axios';
import './App.css';

const App = () => {

  const parseProtoBuf = async (files) => {
    return await new Promise((resolve) => {
      const reader = new FileReader();
      reader.readAsText(files[0]);
      reader.onload = (e) => {
          if(e.target.readyState === FileReader.DONE) {
              const textFile = e.target.result;
              const statement = textFile.split('\n').reduce((acc, line) => {
                  acc.push(line);
                  return acc;
              }, []);

              resolve(ProtoStatements.Statements.encode({ statement }));
          }
      };
    });
  }

  const onSubmit = async (e) => {
    e.preventDefault();
    const inputFile = document.getElementById('file');
    const files = inputFile.files
    if(files.length === 0) {
        alert('Por favor insira um arquivo');
        return;
    }

    const message = await parseProtoBuf(files);
    try {
      const response = await axios.post('http://localhost:8080/api/v1/importStatements', message, { 
        responseType: 'arraybuffer',
        headers: {'Content-Type': 'application/octet-stream'}
      });
      console.log('response', response);
      alert('Arquivo importado com sucesso');
    } catch (error) {
      console.log(error);
      alert('Erro ao importar arquivo');
    }
  }

  const renderInstructions= () => {
    return (
    <>
      <p>Instruções:</p>
      <p>Insira um arquivo CNAB para importa-lo no sistema, cada linha deve possuir 81 caracteres e deve estar conforme o modelo abaixo</p>
      <p>Ex.:3201903010000014200096206760174753****3153153453JOÃO MACEDO   BAR DO JOÃO       </p>
      <p>Caso o valor seja menor que o espaço disponível, o restante deve ser preenchido com espaços em branco</p>
      <p>Se atente as seguintes instruções para formar o arquivo:</p>
    </>);
  }

  const renderTable = () => {
    return (<table>
            <thead>
            <tr>
            <th>Descrição do campo</th>
            <th>Inicio</th>
            <th>Fim</th>
            <th>Tamanho</th>
            <th>Comentário</th>
            </tr>
            </thead>
            <tbody>
            <tr>
            <td>Tipo</td>
            <td>1</td>
            <td>1</td>
            <td>1</td>
            <td>Tipo da transação</td>
            </tr>
            <tr>
            <td>Data</td>
            <td>2</td>
            <td>9</td>
            <td>8</td>
            <td>Data da ocorrência</td>
            </tr>
            <tr>
            <td>Valor</td>
            <td>10</td>
            <td>19</td>
            <td>10</td>
            <td>Valor da movimentação. <em>Obs.</em> O valor encontrado no arquivo precisa ser divido por cem(valor / 100.00) para normalizá-lo.</td>
            </tr>
            <tr>
            <td>CPF</td>
            <td>20</td>
            <td>30</td>
            <td>11</td>
            <td>CPF do beneficiário</td>
            </tr>
            <tr>
            <td>Cartão</td>
            <td>31</td>
            <td>42</td>
            <td>12</td>
            <td>Cartão utilizado na transação</td>
            </tr>
            <tr>
            <td>Hora</td>
            <td>43</td>
            <td>48</td>
            <td>6</td>
            <td>Hora da ocorrência atendendo ao fuso de UTC-3</td>
            </tr>
            <tr>
            <td>Dono da loja</td>
            <td>49</td>
            <td>62</td>
            <td>14</td>
            <td>Nome do representante da loja</td>
            </tr>
            <tr>
            <td>Nome loja</td>
            <td>63</td>
            <td>81</td>
            <td>19</td>
            <td>Nome da loja</td>
            </tr>
            </tbody>
            </table>);
  }

  return (
    <div className="App">
      <header className="app-header flex pl-4">
        <h2>Importações de CNAB_</h2>
      </header>
          <div className="container">
          <div className="flex flex-column">
            {renderInstructions()}
            {renderTable()}
            <hr  style={{
              color: 'red',
              backgroundColor: 'red',
              height: 5
              }}/>
            <form onSubmit={onSubmit}>
              <div className="flex flex-column">
                <input type="file" name="file" id="file" />
                <br/>
                <button type="submit" >Upload</button>
              </div>
            </form>
          </div>
        </div>
    </div>
  );
}

export default App;
