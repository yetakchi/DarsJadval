from typing import Optional, Union, List


class CRUDRepository:
    def all(self) -> List[Union[dict, tuple]]:
        ...

    def create(self, form: dict) -> Union[dict, tuple]:
        ...

    def get(self, idx: int) -> Optional[Union[dict, tuple]]:
        ...

    def update(self, idx: int, form: dict) -> Union[dict, tuple]:
        ...

    def delete(self, idx: int) -> Optional[Union[dict, tuple]]:
        ...
